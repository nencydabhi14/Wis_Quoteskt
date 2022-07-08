package com.example.wisquotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisquotes.databinding.ActivityCategoryScreenBinding

class Category_Screen : AppCompatActivity() {

    lateinit var binding: ActivityCategoryScreenBinding
    private var size: Int = 0
    var list = arrayListOf<ModelData>()
    var color = arrayOf(
        R.color.skyblue,
        R.color.pink,
        R.color.skin,
        R.color.darkp,
        R.color.lgreen,
        R.color.offgrren,
        R.color.plpl,
        R.color.yellow,
        R.color.light,
        R.color.lovender,
        R.color.skyblue,
        R.color.pink,
        R.color.skin,
        R.color.darkp,
        R.color.lgreen,
        R.color.offgrren,
        R.color.plpl,
        R.color.yellow,
        R.color.light,
        R.color.lovender,
        R.color.skyblue,
        R.color.pink,
        R.color.skin,
        R.color.darkp,
        R.color.lgreen,
        R.color.offgrren,
        R.color.plpl,
        R.color.yellow,
        R.color.light,
        R.color.lovender,
        R.color.skyblue,
        R.color.pink,
        R.color.skin,
        R.color.darkp,
        R.color.lgreen,
        R.color.offgrren,
        R.color.plpl,
        R.color.yellow,
        R.color.light,
        R.color.lovender,
        R.color.skyblue,
        R.color.pink,
        R.color.skin,
        R.color.darkp,
        R.color.lgreen,
        R.color.offgrren,
        R.color.plpl,
        R.color.yellow,
        R.color.light,
        R.color.lovender,
        R.color.skyblue,
        R.color.pink,
        R.color.skin,
        R.color.darkp,
        R.color.lgreen,
        R.color.offgrren,
        R.color.plpl,
        R.color.yellow,
        R.color.light,
        R.color.lovender,
        R.color.skyblue,
        R.color.pink,
        R.color.skin,
        R.color.darkp,
        R.color.lgreen,
        R.color.offgrren,
        R.color.plpl,
        R.color.yellow,
        R.color.light,
        R.color.lovender,
        R.color.skyblue,
        R.color.pink,
        R.color.skin,
        R.color.darkp,
        R.color.lgreen,
        R.color.offgrren,
        R.color.plpl,
        R.color.yellow,
        R.color.light,
        R.color.lovender,
        R.color.skyblue,
        R.color.pink,
        R.color.skin,
        R.color.darkp,
        R.color.lgreen,
        R.color.offgrren,
        R.color.plpl,
        R.color.yellow,
        R.color.light,
        R.color.lovender,
        R.color.skyblue,
        R.color.pink,
        R.color.skin,
        R.color.darkp,
        R.color.lgreen,
        R.color.offgrren,
        R.color.plpl,
        R.color.yellow,
        R.color.light,
        R.color.lovender,
        R.color.skyblue,
        R.color.pink,
        R.color.skin,
        R.color.darkp
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCategoryScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        window.statusBarColor = ContextCompat.getColor(this, R.color.blue)

        binding.backCategory.setOnClickListener {
            finish()
        }

    }

    fun setUpRV(l1: ArrayList<ModelData>) {
        var adaptor = Category_Adaptor(this, l1, color)
        { position ->

            var number = l1[position]._id
            var heading = l1[position].name

            var intent = Intent(this, SubQuotes_Category_Screen::class.java)
            intent.putExtra("list", number)
            intent.putExtra("heading", heading)
            startActivity(intent)
            Toast.makeText(this, "Tap to Change Background.. !!!", Toast.LENGTH_SHORT).show()
        }
        var lm = GridLayoutManager(this, 2)
        binding.categoryRcv.layoutManager = lm
        binding.categoryRcv.adapter = adaptor
    }

    override fun onResume() {
        super.onResume()

        Handler(Looper.getMainLooper()).postDelayed({
//            binding.heding.isVisible = true
            binding.categoryRcv.isVisible = true
            binding.all.isVisible = true

        }, 2000)

        var db = Db_helper(this)

        list = db.Read_Category()
        size = list.size
        setUpRV(list)
    }

}