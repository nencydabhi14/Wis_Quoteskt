package com.example.wisquotes

import android.content.*
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.net.Uri
import android.opengl.Visibility
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

class Sub_Quotes_Adaptor(
    val subquotesCategoryScreen: SubQuotes_Category_Screen,
    val l1: ArrayList<ModalQuotes>,
    val image: Array<Int>
) : RecyclerView.Adapter<Sub_Quotes_Adaptor.ViewData>() {

    class ViewData(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var quotes_txt = itemView.findViewById<TextView>(R.id.quotes_txt)
        var change_img_re = itemView.findViewById<RelativeLayout>(R.id.change_img_re)
        var quote = itemView.findViewById<ImageView>(R.id.quote)
        var liked_post = itemView.findViewById<ImageView>(R.id.liked_post)
        var copy_post = itemView.findViewById<ImageView>(R.id.copy_post)
        var save_post = itemView.findViewById<ImageView>(R.id.save_post)
        var share_post = itemView.findViewById<ImageView>(R.id.share_post)
        var insta_category_like = itemView.findViewById<ImageView>(R.id.insta_category_like)
        var vis = itemView.findViewById<RelativeLayout>(R.id.vis)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewData {
        var view =
            LayoutInflater.from(subquotesCategoryScreen).inflate(R.layout.sub_quotes, parent, false)
        return ViewData(view)
    }

    override fun onBindViewHolder(holder: ViewData, position: Int) {
        holder.quotes_txt.text = l1[position].quote
        var text = l1[position].quote

        holder.change_img_re.setOnClickListener {
            val random = Random()
            val n1 = random.nextInt(image.size)

            holder.vis.isVisible = true


            holder.quote.setImageResource(image[n1])
        }

        var clicked = false
        holder.liked_post.setOnClickListener {
            if (clicked) {
                clicked = false;
                holder.liked_post.setImageResource(R.drawable.like);
            } else {
                clicked = true;
                holder.liked_post.setImageResource(R.drawable.liked);
                holder.insta_category_like.isVisible = true

                Toast.makeText(subquotesCategoryScreen, "Liked  !!", Toast.LENGTH_SHORT).show()

                val song: MediaPlayer = MediaPlayer.create(subquotesCategoryScreen, R.raw.like)
                song.start()
            }

            Handler(Looper.getMainLooper()).postDelayed({
                holder.insta_category_like.isVisible = false

            }, 1200)
        }

        holder.copy_post.setOnClickListener {
            val myClipboard =
                subquotesCategoryScreen.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val myClip: ClipData = ClipData.newPlainText("Label", text)
            myClipboard.setPrimaryClip(myClip)
            Toast.makeText(subquotesCategoryScreen, "Copied to your Clipboard", Toast.LENGTH_LONG)
                .show()
        }

        holder.save_post.setOnClickListener {
            holder.change_img_re.setDrawingCacheEnabled(true)
            val bitamp: Bitmap = holder.change_img_re.getDrawingCache()
            try {
                saveBitmap(
                    subquotesCategoryScreen,
                    bitamp,
                    Bitmap.CompressFormat.PNG,
                    "image/*",
                    "newimg.png"
                )
            } catch (e: Exception) {
            }
            Toast.makeText(subquotesCategoryScreen, "Post Saved Successfully  !!!", Toast.LENGTH_SHORT).show()
        }

        holder.share_post.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody =
                "Hello USER,\nPlease Rate Quotes App On Play Store\n⭐️⭐️⭐️⭐️⭐️" +
                        "\n\nYOUR QUOTE\n \uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\n\n $text"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, text)
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            subquotesCategoryScreen.startActivity(Intent.createChooser(sharingIntent, "Share via"))
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