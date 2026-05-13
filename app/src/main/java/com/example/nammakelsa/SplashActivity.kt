package com.example.nammakelsa

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val content = findViewById<View>(R.id.splashContent)
        content.alpha = 0f
        content.scaleX = 0.5f
        content.scaleY = 0.5f
        content.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(800)
            .withEndAction {
                android.os.Handler(mainLooper).postDelayed({
                    startActivity(
                        Intent(this, MainActivity::class.java)
                    )
                    finish()
                }, 1000)
            }
            .start()
    }
}