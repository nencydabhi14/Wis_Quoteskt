package com.example.wisquotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.wisquotes.databinding.ActivityFirstPageBinding

class first_page : AppCompatActivity() {

    lateinit var binding: ActivityFirstPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityFirstPageBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        Handler(Looper.getMainLooper()).postDelayed({
            binding.splasheLogo.isVisible = true
            binding.txt.isVisible = true
            binding.color.isVisible = true
            window.statusBarColor = ContextCompat.getColor(this, R.color.oraange)
        }, 1500)

        Handler(Looper.getMainLooper()).postDelayed({
            val mainIntent = Intent(this,MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, 3500)

    }
}