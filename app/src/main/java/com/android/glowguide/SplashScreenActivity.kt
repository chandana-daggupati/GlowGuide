// SplashScreenActivity.kt
package com.android.glowguide
import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.android.glowguide.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed({
            if(isLoggedIn()){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }
            else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, 4000) // Display splash screen for 2 seconds

    }

    private fun isLoggedIn(): Boolean {
        // Add your logic to check if the user is logged in using SharedPreferences
        // For example, you can use a key like "isLoggedIn" and check its value.
        // Replace this with your actual logic.
        val sharedPreferences = getSharedPreferences("Creds", MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }
}
