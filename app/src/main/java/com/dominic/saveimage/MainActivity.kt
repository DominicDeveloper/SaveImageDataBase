package com.dominic.saveimage

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.dominic.saveimage.Database.Mybase
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var urime:Uri? = null
    lateinit var mybase: Mybase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mybase = Mybase(this)
        btn_take_image.setOnClickListener {
            val list = ArrayList<ImageUser>()
            list.addAll(mybase.getAllImage())
            if (list.isNotEmpty()){
                imageview.setImageURI(Uri.parse(list[0].absolutePath))

                val bitmap = BitmapFactory.decodeByteArray(list[0].image,0,list[0].image!!.size)
                imageview_2.setImageBitmap(bitmap)
            }
        }
        btn_get_image.setOnClickListener {
            getImageNew()

        }
    }

    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()){
            uri:Uri ->
            uri ?: return@registerForActivityResult
            imageview_2.setImageURI(uri)
            val inputStream = contentResolver?.openInputStream(uri)
            val file = File(filesDir,"image.jpg")
            val fileOutputStream = FileOutputStream(file)
            inputStream?.copyTo(fileOutputStream)
            inputStream?.close()
            fileOutputStream?.close()
            val absoulut = file.absolutePath

            val fileInputStream = FileInputStream(file)
            val readBytes  = fileInputStream.readBytes()
            val imageUser = ImageUser(absoulut,readBytes)

            mybase.insertImage(imageUser)

            mybase.getAllImage()

        }

    private fun getImageNew(){
        getImageContent.launch("image/*")
    }


}