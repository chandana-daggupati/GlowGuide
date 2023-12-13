package com.android.glowguide // Your package name

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        // Retrieve the recommended product from intent extras
        val recommendedProduct = intent.getStringExtra("RECOMMENDED_PRODUCT")

        // Set the recommended product in a TextView
        val recommendedProductTextView = findViewById<TextView>(R.id.textRecom)
        recommendedProductTextView.text = "Recommended Product: $recommendedProduct"
    }
    fun openWebLink(view: View) {
        val url = "https://www.nykaa.com/sp/skin-native-desktop/skin" // Replace this with your web link
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}

