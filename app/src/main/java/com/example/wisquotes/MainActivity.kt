package com.example.wisquotes

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.wisquotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.oraange)
        var Db = Db_helper(this)


        binding.categoryRelative.setOnClickListener {
//            Db.Read_Category()
            var intent = Intent(this,Category_Screen::class.java)
            startActivity(intent)
        }

        binding.quoteImg.setOnClickListener {
            var intent = Intent(this,All_Quotes_Screen::class.java)
            startActivity(intent)
        }

        binding.settingsRelative.setOnClickListener {
            var intent = Intent(this,Settings_Screen::class.java)
            startActivity(intent)
        }
    }
}