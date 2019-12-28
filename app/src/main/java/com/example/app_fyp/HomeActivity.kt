package com.example.app_fyp

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.app_fyp.classes.Comic
import com.example.app_fyp.classes.ComicAdapter

class HomeActivity : AppCompatActivity() {
    private var lst : ArrayList<Comic> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        // connect and recieve the contents of the database for this user
        // load them into objects and display objects
        val parent = findViewById<ListView>(R.id.parent)
        loadData()

        val adpater = ComicAdapter(lst, this)

        parent.adapter = adpater

        parent.dividerHeight = 10

    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true

    }*/

    private fun loadData(){
        // parse data (somehow Gson?)
        // make objects imageview, textview,  etc
        // load the data into comic objects
        val c = Comic("hello", "d", 2, "joe")
        val c1 = Comic("there","d", 2, "joe")
        val c2 = Comic("reee","d", 2, "joe")
        val c3 = Comic("csgo","d", 2, "joe")
        lst.add(c)
        lst.add(c1)
        lst.add(c2)
        lst.add(c3)
    }


}