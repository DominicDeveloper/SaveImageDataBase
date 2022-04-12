package com.dominic.saveimage.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dominic.saveimage.ImageUser

class Mybase(context: Context):SQLiteOpenHelper(context,"my_bas",null,1),Interfes {
    override fun onCreate(p0: SQLiteDatabase?) {
        val query = "create table image_table(id integer primary key autoincrement not null, image_path text not null, image blob not null)"
        p0?.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {


    }

    override fun insertImage(imageUser: ImageUser) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("image_path",imageUser.absolutePath)
        contentValues.put("image",imageUser.image)
        db.insert("image_table",null,contentValues)
        db.close()
    }

    override fun getAllImage(): List<ImageUser> {
        val list = ArrayList<ImageUser>()
        val query = "select * from image_table"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query,null)
        if (cursor.moveToFirst()){
            do {
                val imageUser = ImageUser()
                imageUser.id = cursor.getInt(0)
                imageUser.absolutePath = cursor.getString(1)
                imageUser.image = cursor.getBlob(2)
                list.add(imageUser)

            }while (cursor.moveToNext())
        }
        return list

    }
}