package com.example.app_fyp.information.activities

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.transition.Explode
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
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
    private lateinit var pathphoto : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(window){
            exitTransition = Explode()
            sharedElementReturnTransition
            sharedElementReenterTransition
        }

        setContentView(R.layout.activity_addcomic)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        getViews()

        setListeners()

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
                2 -> {
                    /* fill in the data from comicvine if any */
                    val comic = data!!.getSerializableExtra("NEW_COMIC") as Comic?

                    if (comic == null) {
                        Toast.makeText(getApplicationContext(),"No Comic of that name on comicvine.com", Toast.LENGTH_SHORT).show()

                    } else {
                        name.setText(comic.name)
                        issue.setText(1.toString())
                        Glide.with(this).load(comic.image).into(image)
                    }
                }
                else -> {
                    Toast.makeText(getApplicationContext(),"Instructions unclear", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    @Throws(Exception::class)
    private fun setListeners(){
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
            val options = ActivityOptions.makeSceneTransitionAnimation(this, image, "imageTransition")
            startActivityForResult(intent, 2, options.toBundle())
        }
        // set up listeners on the buttons
        add.setOnClickListener{

            val c = buildComic()
            endActivity(c)

        }
    }

    private fun getViews(){
        text = findViewById(R.id.tv)
        name = findViewById(R.id.comic_name)
        issue = findViewById(R.id.issue)
        artist = findViewById(R.id.artist)
        photo = findViewById(R.id.photo)
        add = findViewById(R.id.add_comic)
        image = findViewById(R.id.image)
        search = findViewById(R.id.search)
    }

    @Throws(Exception::class)
    private fun buildComic() : Comic {
        var p : String? = ""
        if ( pathphoto != null ) {
            p = pathphoto
        }
        var a = 1
        try {
            a = issue.text.toString().toInt()
        } catch (e : Exception) {
            errorMessage(text, "Please add a valid issue number")
        }

        return Comic(name.text.toString(), p, a ,artist.text.toString(), this.hashCode(), false, null )

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
            "JPEG_${timeStamp}_", // prefix
            ".jpg", // suffix
            storageDir // directory
        ).apply {
            pathphoto = absolutePath
        }
    }

}