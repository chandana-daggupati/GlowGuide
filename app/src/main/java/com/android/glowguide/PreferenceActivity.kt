package com.android.glowguide
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.MappedByteBuffer
import org.tensorflow.lite.Interpreter

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

            // Save user preferences to Firestore if needed
            val userPreferences = hashMapOf(
                "Skin Type" to selectedSkinType,
                "Hair Type" to hairType
            )

            db.collection("user_preferences")
                .add(userPreferences)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this@PreferenceActivity, "Preferences saved successfully", Toast.LENGTH_SHORT).show()
                    getRecommendation(selectedSkinType, hairType)
                    // Launch RecommendationActivity and pass recommendation
//                    val intent = Intent(this@PreferenceActivity, ProductActivity::class.java)
//                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this@PreferenceActivity, "Preferences saved successfully", Toast.LENGTH_SHORT).show()
                    getRecommendation(selectedSkinType, hairType)
                }
        }
    }

    private fun createByteBuffer(skinType: String, hairType: String): ByteBuffer {
        // Encoding logic for skinType and hairType
        val skinTypeMap = mapOf(
            "Combination" to 0,
            "Normal" to 2,
            "Dry" to 1,
            "Oily" to 3,
            "Sensitive" to 5 // Replace with the numerical representation for "Combination"
            // Add mappings for other skin types if needed
        )

        val hairTypeMap = mapOf(
            "1" to 0,
            "2A" to 1,
            "2B" to 2,
            "2C" to 3,
            "3A" to 4,
            "3B" to 5,
            "3C" to 6,
            "4A" to 7,
            "4B" to 8,
            "4C" to 9 // Replace with the numerical representation for "4A"
            // Add mappings for other hair types if needed
        )

        val encodedSkinType = skinTypeMap[skinType] ?: -1
        val encodedHairType = hairTypeMap[hairType] ?: -1

        val byteBuffer = ByteBuffer.allocateDirect(8)

        byteBuffer.putInt(encodedSkinType)
        byteBuffer.putInt(encodedHairType)

        byteBuffer.flip()

        return byteBuffer
    }

    private fun getRecommendation(skinType: String, hairType: String) {
        val inputBuffer = createByteBuffer(skinType, hairType)
        performInference(inputBuffer)
    }

    private fun performInference(inputBuffer: ByteBuffer) {
        try {
            // Load the TFLite model
            val tfliteModel = loadModelFileFromAsset(this, "recommendation_model.tflite")
            val interpreter = Interpreter(tfliteModel)

            // Prepare input buffer
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 2), DataType.FLOAT32)
            inputFeature0.loadBuffer(inputBuffer)


            // Define your output buffer type and size (modify these according to your model's output)
            val outputBuffer = TensorBuffer.createFixedSize(intArrayOf(1, 49), DataType.FLOAT32)

            // Run inference
            interpreter.run(inputFeature0.buffer, outputBuffer.buffer.rewind())

            // Process the output
            val probabilities: FloatArray = outputBuffer.floatArray // Retrieve the output values as a FloatArray
            Log.d("PROB", probabilities.contentToString())
            // Find the index of the maximum value in the output array
//            val maxIndex = probabilities.indexOf(probabilities.maxOrNull())
            val maxIndex = probabilities.indexOfFirst { it == probabilities.maxOrNull() }

            // Compare with your list of products
            val productList = listOf(
                "Dot & Key Creams and Facewash, SkinKraft",
                "Dot & Key Creams and Facewash, SkinKraft",
                "Dot & Key Creams and Facewash, SkinKraft",
                "Dot & Key Creams and Facewash, SkinKraft",
                "Dot & Key Creams and Facewash, SkinKraft",
                "Dot & Key Creams and Facewash, SkinKraft",
                "Dot & Key Creams and Facewash, SkinKraft",
                "Dot & Key Creams and Facewash, SkinKraft",
                "Dot & Key Creams and Facewash, SkinKraft",
                "Dot & Key Creams and Facewash, SkinKraft",
                "Cetaphil moisturizer and Facewash, SkinKraft",
                "Cetaphil moisturizer and Facewash, SkinKraft",
                "Cetaphil moisturizer and Facewash, SkinKraft",
                "Cetaphil moisturizer and Facewash, SkinKraft",
                "Cetaphil moisturizer and Facewash, SkinKraft",
                "Cetaphil moisturizer and Facewash, SkinKraft",
                "Cetaphil moisturizer and Facewash, SkinKraft",
                "Cetaphil moisturizer and Facewash, SkinKraft",
                "Cetaphil moisturizer and Facewash, SkinKraft",
                "Cetaphil moisturizer and Facewash, SkinKraft",
                "Simple Facewash, SkinKraft",
                "Simple Facewash, SkinKraft",
                "Simple Facewash, SkinKraft",
                "Simple Facewash, SkinKraft",
                "Simple Facewash, SkinKraft",
                "Simple Facewash, SkinKraft",
                "Simple Facewash, SkinKraft",
                "Simple Facewash, SkinKraft",
                "Simple Facewash, SkinKraft",
                "Simple Facewash, SkinKraft",
                "mCaffeine Facewash, SkinKraft",
                "mCaffeine Facewash, SkinKraft",
                "mCaffeine Facewash, SkinKraft",
                "mCaffeine Facewash, SkinKraft",
                "mCaffeine Facewash, SkinKraft",
                "mCaffeine Facewash, SkinKraft",
                "mCaffeine Facewash, SkinKraft",
                "mCaffeine Facewash, SkinKraft",
                "mCaffeine Facewash, SkinKraft",
                "mCaffeine Facewash, SkinKraft",
                "Minimalist serums and Facewash, SkinKraft",
                "Minimalist serums and Facewash, SkinKraft",
                "Minimalist serums and Facewash, SkinKraft",
                "Minimalist serums and Facewash, SkinKraft",
                "Minimalist serums and Facewash, SkinKraft",
                "Minimalist serums and Facewash, SkinKraft",
                "Minimalist serums and Facewash, SkinKraft",
                "Minimalist serums and Facewash, SkinKraft",
                "Minimalist serums and Facewash, SkinKraft",
                "Minimalist serums and Facewash, SkinKraft"
            )

            val recommendedProduct = if (maxIndex != -1 && maxIndex < productList.size) {
                productList[maxIndex]
            } else {
                "No recommendation found" // Replace with a default value or handle this case accordingly
            }

            Log.d("RECOMMENDATION", "Recommended product: $recommendedProduct")
            val intent = Intent(this, ProductActivity::class.java)
            intent.putExtra("RECOMMENDED_PRODUCT", recommendedProduct)
            startActivity(intent)
//            if (productList.contains(recommendedProduct)) {
//                // If the recommended product is in your list, perform necessary actions
//                // For example, update UI, send notification, etc.
//            } else {
//                // Handle the case when the recommended product is not in your list
//            }

            interpreter.close()

        } catch (ex: Exception) {
            // Handle exceptions
            ex.printStackTrace()
        }
    }

    private fun loadModelFileFromAsset(context: Context, modelFilename: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelFilename)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }
}
