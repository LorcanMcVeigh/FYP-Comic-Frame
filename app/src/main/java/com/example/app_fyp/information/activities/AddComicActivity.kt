package com.example.app_fyp.information.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.app_fyp.R
import com.example.app_fyp.classes.Comic
import com.example.app_fyp.search.activities.SearchComicActivity
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddComicActivity : AppCompatActivity(){
    private lateinit var text : TextView
    private lateinit var name : EditText
    private lateinit var issue : EditText
    private lateinit var artist : EditText
    private lateinit var photo : Button
    private lateinit var add : Button
    private lateinit var image : ImageView
    private lateinit var search : Button
    private  var pathphoto : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addcomic)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        text = findViewById(R.id.tv)
        name = findViewById(R.id.comic_name)
        issue = findViewById(R.id.issue)
        artist = findViewById(R.id.artist)
        photo = findViewById(R.id.photo)
        add = findViewById(R.id.add_comic)
        image = findViewById(R.id.image)
        search = findViewById(R.id.search)

        photo.setOnClickListener{
            try {
                dispatchTakePictureIntent()
            } catch (e : Exception) {

            }
        }

        search.setOnClickListener {
            // start new intent
            val intent = Intent(this@AddComicActivity, SearchComicActivity::class.java)
            intent.putExtra("COMIC_NAME", name.text.toString())
            startActivityForResult(intent, 2)
        }
        // set up listeners on the buttons
        add.setOnClickListener{

                val c = buildComic()
                if (c.issue!!.size > 0  ){
                    endActivity(c)
                } else {
                    errorMessage(text, "Only add 1 issue number")
                }

        }
        // if the camera takes a photo then store photo
        // when add is pressed update server
        // add to display
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            when (requestCode) {
                1 ->  {
                    val imageBitmap = data!!.extras!!.get("data") as Bitmap
                    image.setImageBitmap(imageBitmap)
                }
                2 -> { /* fill in the data from comicvine if any */}
                else -> {}
            }
        }
    }


    @Throws(Exception::class)
    private fun buildComic() : Comic {
        var p : String? = ""
        if ( pathphoto != null ) {
            p = pathphoto
        }
        val a = ArrayList<Int>()
        try {
            a.add(issue.text.toString().toInt())

        } catch (e : Exception) {
            errorMessage(text, "Only add 1 issue number")
        }
        Log.v("ASGVBCDSVREEHGBFD", p.toString())
        return Comic(name.text.toString(), p, a ,artist.text.toString(), this.hashCode(), false )

    }

    private fun endActivity(c : Comic){
        val i = Intent()
        i.putExtra("EXTRA_COMIC", c)
        setResult(Activity.RESULT_OK, i)
        finish()
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                takePictureIntent.resolveActivity(packageManager)?.also {
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                        errorMessage(text, "Unable to take photo at this time")
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "com.example.android.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent,1)
                    }
                }
            }
        }
    }

    private fun errorMessage(text : TextView, error : String){
        text.visibility = View.VISIBLE
        text.text = error
        text.setTextColor(Color.WHITE)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            pathphoto = absolutePath
        }
    }

}