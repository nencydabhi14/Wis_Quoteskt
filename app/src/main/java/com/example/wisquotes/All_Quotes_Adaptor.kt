package com.example.wisquotes

import android.content.*
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class All_Quotes_Adaptor(
    val allQuotesScreen: All_Quotes_Screen,
    val l1: ArrayList<ModalQuotes>,
    val image: Array<Int>
) : RecyclerView.Adapter<All_Quotes_Adaptor.ViewData>() {

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var all_quotes_txt = itemView.findViewById<TextView>(R.id.all_quotes_txt)
        var all_quote = itemView.findViewById<ImageView>(R.id.all_quote)
        var all_liked_post = itemView.findViewById<ImageView>(R.id.all_liked_post)
        var all_copy_post = itemView.findViewById<ImageView>(R.id.all_copy_post)
        var all_save_post = itemView.findViewById<ImageView>(R.id.all_save_post)
        var all_share_post = itemView.findViewById<ImageView>(R.id.all_share_post)
        var all_insta_category_like = itemView.findViewById<ImageView>(R.id.all_insta_category_like)
        var all_change_img_re = itemView.findViewById<RelativeLayout>(R.id.all_change_img_re)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view =
            LayoutInflater.from(allQuotesScreen).inflate(R.layout.all_quot_design, parent, false)
        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.all_quotes_txt.text = l1[position].quote
        var text = l1[position].quote

        holder.all_change_img_re.setOnClickListener {
            val random = Random()
            val n1 = random.nextInt(image.size)

            holder.all_quote.setImageResource(image[n1])
        }

        var clicked = false
        holder.all_liked_post.setOnClickListener {
            if (clicked) {
                clicked = false;
                holder.all_liked_post.setImageResource(R.drawable.like);
            } else {
                clicked = true;
                holder.all_liked_post.setImageResource(R.drawable.liked);
                holder.all_insta_category_like.isVisible = true

                Toast.makeText(allQuotesScreen, "Liked  !!", Toast.LENGTH_SHORT).show()

                val song: MediaPlayer = MediaPlayer.create(allQuotesScreen, R.raw.like)
                song.start()
            }

            Handler(Looper.getMainLooper()).postDelayed({
                holder.all_insta_category_like.isVisible = false

            }, 1200)
        }

        holder.all_copy_post.setOnClickListener {
            val myClipboard =
                allQuotesScreen.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val myClip: ClipData = ClipData.newPlainText("Label", text)
            myClipboard.setPrimaryClip(myClip)
            Toast.makeText(allQuotesScreen, "Copied to your Clipboard", Toast.LENGTH_LONG)
                .show()
        }

        holder.all_save_post.setOnClickListener {
            holder.all_change_img_re.setDrawingCacheEnabled(true)
            val bitamp: Bitmap = holder.all_change_img_re.getDrawingCache()
            try {
                saveBitmap(
                    allQuotesScreen,
                    bitamp,
                    Bitmap.CompressFormat.PNG,
                    "image/*",
                    "newimg.png"
                )
            } catch (e: Exception) {
            }
            Toast.makeText(allQuotesScreen, "Post Saved Successfully  !!!", Toast.LENGTH_SHORT).show()
        }

        holder.all_share_post.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody =
                "Hello USER,\nPlease Rate Quotes App On Play Store\n⭐️⭐️⭐️⭐️⭐️" +
                        "\n\nYOUR QUOTE\n \uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\n\n $text"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, text)
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            allQuotesScreen.startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }

    }

    override fun getItemCount(): Int {
        return l1.size
    }


    @Throws(IOException::class)
    fun saveBitmap(
        context: Context,
        bitmap: Bitmap,
        format: Bitmap.CompressFormat,
        mimeType: String,
        displayName: String
    ): Uri? {
        val relativeLocation = Environment.DIRECTORY_DCIM + File.separator + "PhotoMaker"
        val values = ContentValues()
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName)
        values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation)
        val resolver = context.contentResolver
        var uri: Uri? = null
        return try {
            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            uri = resolver.insert(contentUri, values)
            if (uri == null) throw IOException("Failed to create new MediaStore record.")
            resolver.openOutputStream(uri).use { stream ->
                if (stream == null) throw IOException("Failed to open output stream.")
                if (!bitmap.compress(
                        format,
                        95,
                        stream
                    )
                ) throw IOException("Failed to save bitmap.")
            }
            Toast.makeText(context, "" + uri, Toast.LENGTH_SHORT).show()
            uri
        } catch (e: IOException) {
            if (uri != null) {
                // Don't leave an orphan entry in the MediaStore
                resolver.delete(uri, null, null)
            }
            throw e
        }
    }

}