package com.android.glowguide // Your package name

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.android.glowguide.R

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val imageContainer: LinearLayout = findViewById(R.id.imageContainer)

        // Example: List of image resources (Replace with your own images)
        val imageResources = listOf(
            R.drawable.dot_key,
            R.drawable.dry_skin,
            R.drawable.minimalist,
            R.drawable.plum,
            R.drawable.sensitive_skin,
            R.drawable.oily_skin

        )

        // Dynamically create ImageViews and add them to the LinearLayout
        for (imageRes in imageResources) {
            val imageView = ImageView(this)
            imageView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            imageView.setImageResource(imageRes)
            imageView.adjustViewBounds = true
            imageContainer.addView(imageView)
        }
    }
}
