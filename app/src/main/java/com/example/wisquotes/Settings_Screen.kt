package com.example.wisquotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.wisquotes.databinding.ActivitySettingsScreenBinding

class Settings_Screen : AppCompatActivity() {

    lateinit var binding: ActivitySettingsScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySettingsScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.blue)

        CreateShrd(binding.hshEdt.text.toString())
    }

    fun CreateShrd(hash: String) {
        var sharedPref = this.getSharedPreferences("MyPref", MODE_PRIVATE)
        var editor = sharedPref.edit()
        editor.putString("hashtag", hash)
        editor.commit()
    }
}