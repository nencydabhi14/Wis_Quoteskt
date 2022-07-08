package com.example.wisquotes

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class Db_helper(val context: Context?) : SQLiteOpenHelper(context, "quotes.sqlite", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {}
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {}

    var DBNAME = "quotes.sqlite"
    var path: String? = null


    init {
        path = if (Build.VERSION.SDK_INT >= 17) {
            context!!.applicationInfo.dataDir + "/databases/"
        } else {
            "/data/data/" + context!!.packageName + "/databases/"
        }
        copyDB()
        this.readableDatabase
    }

    fun checkDB(): Boolean {
        var datastatus = File(path + DBNAME)
        return datastatus.exists()
    }

    fun copyDB() {
        if (!checkDB()) {
            this.readableDatabase
            close()
            copyFileDatabse()
        }
    }

    fun copyFileDatabse() {
        var dbassets = context!!.assets.open(DBNAME)
        var outputStream: OutputStream = FileOutputStream(path + DBNAME)
        val mBuffer = ByteArray(1024)
        var mLength: Int
        while (dbassets.read(mBuffer).also { mLength = it } > 0) outputStream.write(
            mBuffer,
            0,
            mLength
        )
        outputStream.flush()
        outputStream.close()
        dbassets.close()
    }

    @SuppressLint("Range")
    fun Read_Category(): ArrayList<ModelData> {

        var list = arrayListOf<ModelData>()

        var db = readableDatabase

        var query = "SELECT * FROM category"

        var cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                var _id = cursor.getString(cursor.getColumnIndex("_id"))
                var name = cursor.getString(cursor.getColumnIndex("name"))
                var status = cursor.getString(cursor.getColumnIndex("status"))

//                Log.e("TAG", "readData:===================$_id $name $status")

                var m1 = ModelData(
                    _id,
                    name,
                    status
                )
                list.add(m1)

            } while (cursor.moveToNext())
        }
        return list

    }


    @SuppressLint("Range")
    fun ReadQuote(id : String): ArrayList<ModalQuotes> {

        var list_q = arrayListOf<ModalQuotes>()

        var db = readableDatabase

        var query = "SELECT * FROM quotes where category_id = $id"

        var cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                var _id = cursor.getString(cursor.getColumnIndex("_id"))
                var category_id = cursor.getString(cursor.getColumnIndex("category_id"))
                var quote = cursor.getString(cursor.getColumnIndex("quote"))
                var utp = cursor.getString(cursor.getColumnIndex("utp"))

//              Log.e("TAG", "readData:===================$_id $category_id $quote $utp")

                var mq1 = ModalQuotes(
                    _id,
                    category_id,
                    quote,utp
                )
                list_q.add(mq1)

            } while (cursor.moveToNext())
        }
        return list_q

    }

    @SuppressLint("Range")
    fun Read_All_Quote(): ArrayList<ModalQuotes> {

        var list_q = arrayListOf<ModalQuotes>()

        var db = readableDatabase

        var query = "SELECT * FROM quotes"

        var cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                var _id = cursor.getString(cursor.getColumnIndex("_id"))
                var category_id = cursor.getString(cursor.getColumnIndex("category_id"))
                var quote = cursor.getString(cursor.getColumnIndex("quote"))
                var utp = cursor.getString(cursor.getColumnIndex("utp"))

//              Log.e("TAG", "readData:===================$_id $category_id $quote $utp")

                var mq1 = ModalQuotes(
                    _id,
                    category_id,
                    quote,utp
                )
                list_q.add(mq1)

            } while (cursor.moveToNext())
        }
        return list_q

    }




}