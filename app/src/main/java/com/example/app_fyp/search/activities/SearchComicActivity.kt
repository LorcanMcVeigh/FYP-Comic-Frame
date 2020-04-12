package com.example.app_fyp.search.activities

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.example.app_fyp.R
import com.example.app_fyp.classes.Comic
import com.example.app_fyp.search.comicvine.ComicResult
import com.example.app_fyp.search.comicvine.ComicVineAPIQuery
import kotlinx.android.synthetic.main.item_layout.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchComicActivity : AppCompatActivity() {
    lateinit var rl : LinearLayout
    var comicname : String = ""
    lateinit var comiclst : ArrayList<Comic>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window){
            sharedElementReturnTransition
            sharedElementReenterTransition
        }
        setContentView(R.layout.activity_searchcomic)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        getViews()

        val i = intent.getStringExtra("COMIC_NAME")

        Search(i)
    }

    private fun getViews(){
        rl = findViewById(R.id.ll2)
    }


    private fun Search(name: String?) {
        comicname = name!!.replace(" ", "%20")

        val data = ComicVineAPIQuery(comicname)
        runBlocking {
            data.loadJson()
        }
        while (data.data.size <= 0) {
            Thread.sleep(1000)
        }

        displayResults(data.data)


    }

    private fun endActivity( t : TextView, url : String){
        val c = Comic(t.text.toString(), url, 1,null,hashCode(),false,null )
        val i = Intent()
        i.putExtra("NEW_COMIC", c)
        setResult(Activity.RESULT_OK, i)
        finishAfterTransition()
    }


    private fun displayResults(content : ArrayList<ComicResult>) {
        Log.v("AWIBFLKJHBFSDKFKSAB", content.toString())
        val cont = content.iterator()
        var cr: ComicResult
        var cr2: ComicResult? = null
        while (cont.hasNext()) {
            cr = cont.next()

            if (cont.hasNext()) {
                cr2 = cont.next()
            }
            val inflater: LayoutInflater =
                getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.a_comic, null)
            //val inflater = LayoutInflater.from(parent.context).inflate(R.layout.a_comic, parent, false)
            if (!cr.image["thumb_url"].isNullOrEmpty()) {

                val b = view.findViewById<ImageView>(R.id.first)
                val tv1 = view.findViewById<TextView>(R.id.tv1)
                val image : String = cr.image["thumb_url"].toString()
                Glide.with(this).load(image).into(b)
                tv1.text = cr.name

                b.setOnClickListener {
                    b.transitionName = "imageTransition"
                    //tv1.transitionName = "textTransition"
                    endActivity( tv1, image )
                }
            }
            if (!cr2!!.image["thumb_url"].isNullOrEmpty()) {
                val b = view.findViewById<ImageView>(R.id.second)
                val tv2 = view.findViewById<TextView>(R.id.tv2)
                val image : String = cr2.image["thumb_url"].toString()
                Glide.with(this).load(image).into(b)
                tv2.text = cr2.name

                b.setOnClickListener {
                    b.transitionName = "imageTransition"
                    //tv2.transitionName = "textTransition"
                    endActivity( tv2, image )
                }

            }

            rl.addView(view)
        }
    }

    private fun insertComic(cr : ComicResult, v : View): View{
        if (cr!!.image["thu"])


    }

}