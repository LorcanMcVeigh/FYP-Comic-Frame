package com.example.app_fyp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_fyp.classes.Comic
import com.example.app_fyp.classes.ComicAdapter
import kotlinx.android.synthetic.main.comic_card_layout.view.*
import java.util.*

class HomeActivity : AppCompatActivity() {
    private var ADD_COMIC_REQUEST = 1
    private var UPDATE_COMIC_ISSSUE = 2
    private var lst : ArrayList<Comic> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        // connect and recieve the contents of the database for this user
        // load them into objects and display objects

        displayContent()


    }

    override fun onRestart() {
        super.onRestart()
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
        val comic = data!!.getSerializableExtra("COMIC") as Comic
        when ( requestCode ) {
            ADD_COMIC_REQUEST -> { if (resultCode == RESULT_OK) {
                lst.add(comic)
            } else {
                Toast.makeText(getApplicationContext(),"Unable to add Comic", Toast.LENGTH_SHORT).show();
            } }
            UPDATE_COMIC_ISSSUE -> { if (resultCode == Activity.RESULT_OK){
                super.onActivityResult(requestCode, resultCode, data)
                lst.remove(lst.find{ it.hash == comic.hash})
                lst.add(comic)
            } else if (resultCode == Activity.RESULT_CANCELED){
                super.onActivityResult(requestCode, resultCode, data)

                if (comic.name == "") {
                    Toast.makeText(getApplicationContext(),"Unable to update Comic",Toast.LENGTH_SHORT).show();
                } else {
                    lst.remove(lst.find{ it.hash == comic.hash})
                }
            } else {
                Toast.makeText(getApplicationContext(),"Comic list unable to updated",Toast.LENGTH_SHORT).show();
            }}
        }

        displayContent()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    private fun displayContent(sort : String = "name"){
        loadData()

        when(sort){
            "name" -> lst.sortedWith(compareBy{ it.name })
            "artist" -> lst.sortedWith(compareBy{ it.artist })
            else -> lst.sortedWith(compareBy{ it.name })
        }

        val parent = findViewById<RecyclerView>(R.id.recyclerview)
        parent.layoutManager = LinearLayoutManager(this)
        parent.adapter = ComicAdapter(lst, this)

        val count = parent.childCount
        Log.v("FA: Child count", count.toString())
        var i = 0
        while ( i < count ){
            val c : View = parent.getChildAt(i)
            c.setOnClickListener {
                startNewActivity(c.ll.tv1.text.toString())
            }
            i++
        }
    }

    private fun startNewActivity(id : String) {

        val intent = Intent(this@HomeActivity, ComicActivity::class.java)
        // find comic reference in lst list
        val comic = lst.find { it.name == id  }
        intent.putExtra("COMIC", comic)
        startActivityForResult(intent, UPDATE_COMIC_ISSSUE)
    }

    private fun loadData() {
        // parse data (somehow Gson?)
        // make objects imageview, textview,  etc
        // load the data into comic objects
        // val view = ArrayList<CardView>()
        if (lst.size < 1) {
            val a = ArrayList<Int>()
            a.add(2)
            lst.add(Comic("X-men Dark Phoenix", "d", a, "joe", 1))
            lst.add(Comic("The Flash and Green Lantern", "d", a, "joe",2))
            lst.add(Comic("Katy Keene", "d", a, "joe",3))
            a.add(3)
            a.add(5)
            a.add(4)
            a.add(6)
            a.add(7)
            a.add(8)
            a.add(9)
            lst.add(Comic("Great Comics", "d", a, "joe",4))

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
