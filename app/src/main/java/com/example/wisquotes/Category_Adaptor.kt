package com.example.wisquotes

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.graphics.blue
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.RecyclerView

class Category_Adaptor(
    val categoryScreen: Category_Screen,
    val l1: ArrayList<ModelData>,
    val color: Array<Int>, var click: ((Int) -> Unit)? = null
) : RecyclerView.Adapter<Category_Adaptor.ViewData>() {

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var category_main = itemView.findViewById<TextView>(R.id.category_main)
        var line_relative = itemView.findViewById<RelativeLayout>(R.id.line_relative)
        var quotes = itemView.findViewById<RelativeLayout>(R.id.quotes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view =
            LayoutInflater.from(categoryScreen).inflate(R.layout.category_design, parent, false)
        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.category_main.text = l1[position].name
        holder.category_main.setTextColor(categoryScreen.getResources().getColor(color[position]))
        holder.line_relative.setBackgroundColor(
            categoryScreen.getResources().getColor(color[position])
        )
        holder.quotes.setOnClickListener {
//            var number = l1[position]._id
//            var heading = l1[position].name
//
//            var intent = Intent(categoryScreen, SubQuotes_Category_Screen::class.java)
//            intent.putExtra("list", number)
//            intent.putExtra("heading", heading)
//            categoryScreen.startActivity(intent)

//            var db = Db_helper(categoryScreen)
//
//            db.ReadQuote()
////            Toast.makeText(this, "" + ps, Toast.LENGTH_SHORT).show()

            click?.invoke(position)
        }

    }

    override fun getItemCount(): Int {
        return l1.size
    }

}