package com.example.app_fyp.search.activities

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.app_fyp.R
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

        ComicVineAPIQuery(comicname)
    }



    private fun displayResults(content : ArrayList<ComicResult>) {
        Log.v("AWIBFLKJHBFSDKFKSAB", content[1].image.toString())
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