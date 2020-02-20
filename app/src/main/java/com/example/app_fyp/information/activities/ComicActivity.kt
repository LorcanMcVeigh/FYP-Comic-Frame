package com.example.app_fyp.information.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.app_fyp.R
import com.example.app_fyp.classes.Comic
import kotlin.Exception

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        image = findViewById(R.id.image)
        tv1 = findViewById(R.id.tv1)
        tv2 = findViewById(R.id.tv2)
        ed1 = findViewById(R.id.et1)
        ed2 = findViewById(R.id.et2)
        b1 = findViewById(R.id.b1)
        b2 = findViewById(R.id.b2)
        update = findViewById(R.id.update)

        tv1.setOnClickListener {
            ed1.setText(tv1.text)
            ed1.visibility = View.VISIBLE
            b1.visibility = View.VISIBLE
        }

        tv2.setOnClickListener {
            ed2.setText(tv2.text)
            ed2.visibility = View.VISIBLE
            b2.visibility = View.VISIBLE
        }

        b1.setOnClickListener {
            tv1.setText(ed1.text)
            ed1.visibility = View.GONE
            b1.visibility = View.GONE
        }

        b2.setOnClickListener {
            tv2.setText(ed2.text)
            ed2.visibility = View.GONE
            b2.visibility = View.GONE
        }

        update.setOnClickListener {
            val c = buildComic()
            endActivity( "COMIC", Activity.RESULT_OK,c)
        }


        data = intent.getSerializableExtra("COMIC") as Comic

        fillinData(data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.comic_menu, menu)
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



    private fun fillinData(data : Comic){
        image.setImageResource(R.drawable.c1)
        tv1.setText(data.name)
        tv2.setText(data.issue!!.joinToString(prefix = "Issues : ", separator = ","))
        //Glide.with(this).load(data.image!!).into(image)
    }

    @Throws(Exception::class)
    private fun buildComic() : Comic {

        val i = ArrayList<Int>()
        val issues = tv2.text.filter { it.toString() == "," || it.toString() == "Issues : " }
        for (l in issues) {
            try {
                i.add(l.toInt())
            } catch (e : Exception) {
                endActivity("COMIC", Activity.RESULT_CANCELED, null)
            }
        }

        data.name = tv1.text.toString()
        data.issue = i

        return data
    }

    private fun endActivity(name : String, result : Int, c : Comic?){
        val i = Intent()
        i.putExtra(name, c)
        setResult(result, i)
        finish()
    }
}