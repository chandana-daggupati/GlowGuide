package com.android.glowguide

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RecommendationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendation)

        // Retrieve data from the intent extras
        val skinType = intent.getStringExtra("SkinType")
        val hairType = intent.getStringExtra("HairType")
        val recommendedProduct = intent.getStringExtra("RecommendedProduct")

        // Display the data in TextViews
        val skinTypeTextView: TextView = findViewById(R.id.skinProductsTextView)
        val hairTypeTextView: TextView = findViewById(R.id.hairProductsTextView)
        val recommendedProductTextView: TextView = findViewById(R.id.textRecom)

        skinTypeTextView.text = "Skin Type: $skinType"
        hairTypeTextView.text = "Hair Type: $hairType"
        recommendedProductTextView.text = "Recommended Product: $recommendedProduct"
    }
}
