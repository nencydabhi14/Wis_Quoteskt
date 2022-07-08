package com.example.wisquotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wisquotes.databinding.ActivityAllQuotesScreenBinding

class All_Quotes_Screen : AppCompatActivity() {

    lateinit var binding: ActivityAllQuotesScreenBinding
    var list = arrayListOf<ModalQuotes>()
    private var size: Int = 0

    var image = arrayOf(
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3,
        R.drawable.img4,
        R.drawable.img5,
        R.drawable.img6, R.drawable.img7,
        R.drawable.img8,
        R.drawable.img9,
        R.drawable.img10
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAllQuotesScreenBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.blue)

        binding.backQuote.setOnClickListener {
            onBackPressed()
        }

    }

    fun setUpRV(l1: ArrayList<ModalQuotes>) {
        var adaptor = All_Quotes_Adaptor(this, l1, image)
        var lm = LinearLayoutManager(this)
        binding.allQuotRcv.layoutManager = lm
        binding.allQuotRcv.adapter = adaptor
    }

    override fun onResume() {
        super.onResume()

        Handler(Looper.getMainLooper()).postDelayed({
            binding.allQuotRcv.isVisible = true
            binding.all.isVisible = true
        }, 2000)

        var db = Db_helper(this)

        list = db.Read_All_Quote()
        size = list.size
        setUpRV(list)
    }


}