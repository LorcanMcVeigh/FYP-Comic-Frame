package com.example.app_fyp

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.cardview.widget.CardView
import com.example.app_fyp.classes.Comic
import com.example.app_fyp.classes.ComicAdapter
import java.util.*

class HomeActivity : AppCompatActivity() {
    private lateinit var lst : ArrayList<Comic>
    private lateinit var viewManager: LinearLayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        // connect and recieve the contents of the database for this user
        // load them into objects and display objects

        val data = loadData()
        viewManager = LinearLayoutManager(this)
        viewAdapter= ComicAdapter(lst, this)

        val parent = findViewById<RecyclerView>(R.id.parent)
        // loop over cards and add them
        // parent.addView(card)
        parent.adapter = viewAdapter
        parent.layoutManager = viewManager
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun loadData() : List<CardView>{
        // parse data (somehow Gson?)
        // make objects imageview, textview,  etc
        // load the data into comic objects
        lst = ArrayList<Comic>()
        lst.add(Comic("hello", "d", 2, "joe"))
        lst.add(Comic("there","d", 2, "joe"))
        lst.add(Comic("reee","d", 2, "joe"))
        lst.add(Comic("csgo","d", 2, "joe"))
        lst.add(Comic("hello", "d", 3, "joe"))
        lst.add(Comic("hello", "d", 5, "joe"))
        lst.add(Comic("hello", "d", 4, "joe"))
    }

    private fun createCard() : CardView{
        val card = CardView(this)
        val layoutparams = LinearLayout.LayoutParams(
            300, 100
        ).apply{
            gravity = Gravity.CENTER
        }
        layoutparams.topMargin = 10
        layoutparams.marginStart = 15
        layoutparams.marginEnd = 15
        card.layoutParams = layoutparams
        card.radius = 10F

        return card
    }

    private fun createTextView() : TextView {
        val t = TextView(this)
        val layout = LinearLayout.LayoutParams()
    }

}