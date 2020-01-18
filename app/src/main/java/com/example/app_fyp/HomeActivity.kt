package com.example.app_fyp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.app_fyp.classes.Comic
import com.example.app_fyp.classes.ComicAdapter
import java.util.*

class HomeActivity : AppCompatActivity() {
    private var ADD_COMIC_REQUEST = 1
    private var lst : ArrayList<Comic> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        // connect and recieve the contents of the database for this user
        // load them into objects and display objects

        displayContent()


    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        when (item.itemId) {
            R.id.add_comic -> {
                val intent = Intent(this@HomeActivity, AddComicActivity::class.java)
                startActivityForResult(intent, ADD_COMIC_REQUEST)

            }

            R.id.sort -> {
                // pop-up menu to choose a feature to sort by

            }

            else -> {
                super.onOptionsItemSelected(item)
            }

        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ADD_COMIC_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val comic = intent.extras!!.getSerializable("EXTRA_COMIC") as Comic
                val cc = lst.size
                lst.add(comic)
                val c = lst.size
                if (cc != c) {
                    finish()
                }
                displayContent()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        displayContent()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    private fun displayContent(sort : String = "name"){
        loadData()
        val parent = findViewById<RecyclerView>(R.id.recyclerview)
        parent.layoutManager = LinearLayoutManager(this)
        parent.adapter = ComicAdapter(lst, this)
        // loop over cards and add them
        // parent.addView(card)
        /*for (c in data){
            parent.addView(c)
        }*/
    }

    private fun loadData() {
        // parse data (somehow Gson?)
        // make objects imageview, textview,  etc
        // load the data into comic objects
        // val view = ArrayList<CardView>()
        if (lst.size < 1) {
            var a = ArrayList<Int>()
            a.add(2)
            lst.add(Comic("X-men Dark Phoenix", "d", a, "joe"))
            lst.add(Comic("The Flash and Green Lantern", "d", a, "joe"))
            lst.add(Comic("Katy Keene", "d", a, "joe"))
            a.add(3)
            a.add(5)
            a.add(4)
            a.add(6)
            a.add(7)
            a.add(8)
            a.add(9)
            lst.add(Comic("Great Comics", "d", a, "joe"))
        }

        /*var count = 0
        for (c in lst) {
            count++
            // create a card
            var card = createCard()
            //create a linear layout
            val ll = createll(count)
            var tv1 = createTextView(false)
            tv1 = adapter(c.name, tv1)
            ll.addView(tv1, 0)
            var tv2 = createTextView(true)
            val s =  c.issue?.joinToString(prefix = "Issues : ", limit= 4, separator = ",", truncated=".."+c.issue[c.issue.size -1].toString())
            tv2 = adapter(s, tv2)
            ll.addView(tv2, 1)
            // create the textviews for the card
            // add the card to the activity
            card.addView(ll)
            view.add(card)
        }*/

    }

}