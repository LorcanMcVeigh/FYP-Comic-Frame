package com.example.app_fyp.information.activities

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.TransitionManager
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import com.example.app_fyp.R
import com.example.app_fyp.classes.Comic
import kotlin.Exception
import android.util.Pair as UtilPair

class ComicActivity : AppCompatActivity(){
    private lateinit var image : ImageView
    private lateinit var tv1 : TextView
    private lateinit var tv2 : TextView
    private lateinit var ed1 : EditText
    private lateinit var ed2 : EditText
    private lateinit var b1 : Button
    private lateinit var b2 : Button
    private lateinit var update : Button
    private lateinit var data : Comic
    private lateinit var t : Toolbar
    private lateinit var gn : ArrayList<String>
    private lateinit var rl : RelativeLayout
    private var nameChange : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setting simple activity transitions
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            sharedElementReturnTransition
            sharedElementExitTransition
            allowEnterTransitionOverlap
            allowReturnTransitionOverlap
        }

        setContentView(R.layout.activity_comic)

        getData()


        setToolbarTitle()
        setSupportActionBar(t)

        getViews()

        setListeners()


        fillinData(data)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.comic_menu, menu)


        val subm = menu!!.getItem(2).subMenu
        val ind : Int =  0
        for (i in gn) {
            subm.add(2,ind, 0 ,i)
            val d = subm.getItem(ind)
            d.setOnMenuItemClickListener {
                data.group = d.title.toString()
                Toast.makeText(applicationContext,"${data.name} has been added to ${d.title} ",Toast.LENGTH_SHORT).show()
                true
            }

        }
        return true

    }


    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        when (item.itemId) {
            R.id.delete -> {
                endActivity("COMIC", Activity.RESULT_CANCELED, data)
            }
            R.id.favorite -> {
                data.isFave = !data.isFave
            }

            else -> {
                super.onOptionsItemSelected(item)
            }

        }
        return true
    }

    private fun setToolbarTitle() {
        // sets the title on the toobar for the activity
        val t : Toolbar? = findViewById(R.id.my_toolbar)
        t!!.title = data.name
    }

    private fun setListeners(){
        /*
        * sets listeners on the buttons and textboxs
        * so that the user can change the title and issue number
        */


        tv1.setOnClickListener {
            ed1.setText(tv1.text)

            setVisiblity(ed1, tv1, b1, 1)

        }

        tv2.setOnClickListener {
            ed2.setText(tv2.text)

            setVisiblity(ed2, tv2, b2, 1 )
        }

        b1.setOnClickListener {
            tv1.setText(ed1.text)
            nameChange = true

            setVisiblity(ed1, tv1, b1, -1)

        }

        b2.setOnClickListener {
            t.setTitle(ed2.text)
            tv2.setText(ed2.text)
            setVisiblity(ed2, tv2, b2, -1)
        }

        update.setOnClickListener {
            val c = buildComic()
            image.transitionName = "image"
            tv1.transitionName = "text"
            endActivity( "COMIC", Activity.RESULT_OK,c)
        }

    }

    fun setVisiblity(e : EditText, t : TextView, b : Button, i : Int ){
        when (i) {
            1 -> {
                t.visibility = View.GONE
                e.visibility = View.VISIBLE
                b.visibility = View.VISIBLE
            }
            -1 -> {
                ed1.visibility = View.GONE
                tv1.visibility = View.VISIBLE
                b1.visibility = View.GONE
            }

            else -> {
                true
            }
        }

    }

    private fun getViews() {
        // gets all the views in the activity

        rl = findViewById(R.id.rl)
        image = findViewById(R.id.image)
        image.transitionName = "image"
        tv1 = findViewById(R.id.tv1)
        tv1.transitionName = "text"
        tv2 = findViewById(R.id.tv2)
        ed1 = findViewById(R.id.et1)
        ed2 = findViewById(R.id.et2)
        b1 = findViewById(R.id.b1)
        b2 = findViewById(R.id.b2)
        update = findViewById(R.id.update)
    }

    private fun fillinData(data : Comic){
        // fills in the data into the views in the activity
        image.setImageResource(R.drawable.c1)
        tv1.text = data.name
        tv2.text = data.issue.toString()
        //Glide.with(this).load(data.image!!).into(image)
    }


    @Throws(Exception::class)
    private fun buildComic() : Comic {
        // builds a comic object
        data.name = tv1.text.toString()
        try {
            data.issue = tv2.text.toString().toInt()
        } catch ( e: Exception) {
            data.issue = 1
        }

        return data
    }

    private fun getData(){
        /* custom function that recieves and load data from an intent into
           custom data objects */
        data = intent.getSerializableExtra("COMIC") as Comic
        gn = intent.getStringArrayListExtra("GROUPNAMES") as ArrayList<String>
    }

    private fun endActivity(name : String, result : Int, c : Comic?){
        val i = Intent()
        i.putExtra(name, c)
        Log.v("HNAIKVNSKVRNWKVNDSKV", c!!.issue.toString())
        setResult(result, i)

        finishAfterTransition()



    }
}