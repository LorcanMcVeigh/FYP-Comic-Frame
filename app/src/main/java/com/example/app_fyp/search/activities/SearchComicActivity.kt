package com.example.app_fyp.search.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import co.metalab.asyncawait.async
import com.bumptech.glide.Glide
import com.example.app_fyp.R
import com.example.app_fyp.classes.Comic
import com.example.app_fyp.search.comicvine.ComicResult
import com.example.app_fyp.search.comicvine.ComicVineAPIQuery

class SearchComicActivity : AppCompatActivity() {
    lateinit var rl : RelativeLayout
    var comicname : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchcomic)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        rl = findViewById(R.id.rl)

        val i = intent.getStringExtra("COMIC_NAME")

        Search(i)
    }

    private fun Search(name: String) {
        comicname = name.replace(" ", "%20")

        val data = ComicVineAPIQuery(comicname)
        data.loadJson()

        suspend {

        }
        Log.v("AWIBFLKJHBFSDKFKSAB", data.search)
        while (data.data.size < 0 ){
           continue
        }
        displayResults(data.data)


    }

    private fun endActivity(c : Comic?){
        val i = Intent()
        i.putExtra("NEW_COMIC", c)
        setResult(Activity.RESULT_OK, i)
        finish()
    }


    private fun displayResults(content : ArrayList<ComicResult>) {
        Log.v("AWIBFLKJHBFSDKFKSAB", content.toString())
        val cont = content.iterator()
        var num = 0
        while (cont.hasNext()){
            val cr = cont.next()
            if (cr.image["thumb_url"].isNullOrEmpty()) {
                //Log.v("AWIBFLKJHBFSDKFKSAB", it.images.get("thumb_url").toString())
                val b = buildImageView(num)
                Glide.with(this).load(cr.image["thumb_url"].toString()).into(b)
                rl.addView(b)
            }
            num++
        }
    }

    private fun buildImageView(n: Int) : ImageView {
        val image = ImageView(this)
        val params = RelativeLayout.LayoutParams(300, 900)
        if (n % 2 == 0){
            params.setMargins(15,10,15,5)
        }
        image.layoutParams = params
        return image
    }
}