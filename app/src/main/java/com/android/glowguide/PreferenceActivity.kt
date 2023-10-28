package com.android.glowguide
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

<<<<<<< HEAD
=======

import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

>>>>>>> d37817a (Initial commit)
class PreferenceActivity : AppCompatActivity() {
    private lateinit var skinTypeRadioGroup: RadioGroup
    private lateinit var hairTypeEditText: EditText
    private lateinit var saveButton: Button
<<<<<<< HEAD
=======
    private lateinit var db: FirebaseFirestore
>>>>>>> d37817a (Initial commit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

<<<<<<< HEAD
=======
        db = FirebaseFirestore.getInstance()
>>>>>>> d37817a (Initial commit)
        skinTypeRadioGroup = findViewById(R.id.skinTypeRadioGroup)
        hairTypeEditText = findViewById(R.id.hairTypeEditText)
        saveButton = findViewById(R.id.saveButton)

        saveButton.setOnClickListener {
            val selectedSkinTypeId = skinTypeRadioGroup.checkedRadioButtonId
<<<<<<< HEAD
            val selectedSkinType = findViewById<RadioButton>(selectedSkinTypeId)?.text.toString()

            val hairType = hairTypeEditText.text.toString()

            val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("skin_type", selectedSkinType)
            editor.putString("hair_type", hairType)
            editor.apply()

            finish()
=======
            var selectedSkinType = ""

            if (selectedSkinTypeId != -1) {
                selectedSkinType = findViewById<RadioButton>(selectedSkinTypeId).text.toString()
            }

            val hairType = hairTypeEditText.text.toString()

            val userPreferences = hashMapOf(
                "Skin Type" to selectedSkinType,
                "Hair Type" to hairType
            )

            db.collection("user_preferences")
                .add(userPreferences)
                .addOnSuccessListener(OnSuccessListener<DocumentReference> { documentReference ->
                    Toast.makeText(this@PreferenceActivity, "Preferences saved successfully", Toast.LENGTH_SHORT).show()
                })
                .addOnFailureListener(OnFailureListener { e ->
                    Toast.makeText(this@PreferenceActivity, "Failed to save preferences", Toast.LENGTH_SHORT).show()
                })
>>>>>>> d37817a (Initial commit)
        }
    }
}
