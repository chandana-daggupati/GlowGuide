package com.android.glowguide

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import com.google.firebase.firestore.FirebaseFirestore

import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer


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

            // Create inputs for the TensorFlow Lite model
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 2), DataType.FLOAT32)
            val byteBuffer = createByteBuffer(selectedSkinType, hairType)
            inputFeature0.loadBuffer(byteBuffer)



            // Save user preferences to Firestore if needed
            val userPreferences = hashMapOf(
                "Skin Type" to selectedSkinType,
                "Hair Type" to hairType
            )
            getRecommendation(selectedSkinType,hairType)

            db.collection("user_preferences")
                .add(userPreferences)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this@PreferenceActivity, "Preferences saved successfully", Toast.LENGTH_SHORT).show()

                    // Launch RecommendationActivity and pass recommendation
                    val intent = Intent(this@PreferenceActivity, RecommendationActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this@PreferenceActivity, "Failed to save preferences", Toast.LENGTH_SHORT).show()
                }
        }
    }

    // Function to create the ByteBuffer for model input (Replace this with your logic)
    private fun createByteBuffer(skinType: String, hairType: String): java.nio.ByteBuffer {
        // Implement your logic to encode skinType and hairType as bytes in the required format
        // Return the ByteBuffer with the encoded data
        return java.nio.ByteBuffer.allocate(0) // Replace with your actual encoding logic
    }

    // Function to process the model output (Replace this with your logic)
    private fun getRecommendation(skinType: String,hairType: String)
    {
        val url = "http://20.40.96.57:3000/recommendation"
        val requestBody = JSONObject()
        requestBody.put("skin_type", skinType)
        requestBody.put("hair_type", hairType)
        val client = OkHttpClient()
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = requestBody.toString().toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    val jsonObject = JSONObject(responseData)
                    val recommendedProduct = jsonObject.getString("recommended_product")
                    println("Recommended product: $recommendedProduct")
                    val sharedPreferences: SharedPreferences = getSharedPreferences("Recommendation", Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("recommendedProduct", recommendedProduct)

                    editor.apply()
                } else {
                    println("Error: ${response.code}")
                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                println("Failed to execute request: ${e.message}")
            }
        })


    }
}
