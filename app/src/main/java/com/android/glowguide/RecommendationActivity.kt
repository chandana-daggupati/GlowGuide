package com.android.glowguide

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RecommendationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendation)

        // Get the recommendation string from the intent
        val recommendation = intent.getStringExtra("Recommendation")

        // Display the recommendation in the TextView
        val recommendationTextView: TextView = findViewById(R.id.recommendationText)
        recommendationTextView.text = recommendation
    }
}
