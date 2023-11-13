package com.android.glowguide
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class PreferenceActivity : AppCompatActivity() {
    private lateinit var skinTypeRadioGroup: RadioGroup
    private lateinit var hairTypeEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        db = FirebaseFirestore.getInstance()
        skinTypeRadioGroup = findViewById(R.id.skinTypeRadioGroup)
        hairTypeEditText = findViewById(R.id.hairTypeEditText)
        saveButton = findViewById(R.id.saveButton)

        saveButton.setOnClickListener {
            val selectedSkinTypeId = skinTypeRadioGroup.checkedRadioButtonId

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

        }
    }
}
