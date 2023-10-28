package com.android.glowguide
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class PreferenceActivity : AppCompatActivity() {
    private lateinit var skinTypeRadioGroup: RadioGroup
    private lateinit var hairTypeEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        skinTypeRadioGroup = findViewById(R.id.skinTypeRadioGroup)
        hairTypeEditText = findViewById(R.id.hairTypeEditText)
        saveButton = findViewById(R.id.saveButton)

        saveButton.setOnClickListener {
            val selectedSkinTypeId = skinTypeRadioGroup.checkedRadioButtonId
            val selectedSkinType = findViewById<RadioButton>(selectedSkinTypeId)?.text.toString()

            val hairType = hairTypeEditText.text.toString()

            val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("skin_type", selectedSkinType)
            editor.putString("hair_type", hairType)
            editor.apply()

            finish()
        }
    }
}
