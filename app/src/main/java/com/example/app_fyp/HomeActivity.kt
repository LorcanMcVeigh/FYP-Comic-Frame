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
import androidx.core.content.ContextCompat
import com.example.app_fyp.classes.Comic
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

        val parent = findViewById<LinearLayout>(R.id.parent)
        // loop over cards and add them
        // parent.addView(card)
        for (c in data){
            parent.addView(c)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    private fun loadData() : ArrayList<CardView>{
        // parse data (somehow Gson?)
        // make objects imageview, textview,  etc
        // load the data into comic objects
        val view = ArrayList<CardView>()
        lst = ArrayList<Comic>()
        var a = ArrayList<Int>()
        a.add(2)
        lst.add(Comic("there","d", a, "joe"))
        lst.add(Comic("reee","d", a, "joe"))
        lst.add(Comic("csgo","d", a, "joe"))
        a.add(3)
        a.add(5)
        a.add(4)
        a.add(6)
        a.add(7)
        a.add(8)
        a.add(9)
        lst.add(Comic("hello", "d", a, "joe"))
        var count = 0
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
        }

        return view
    }

    private fun createll(count : Int) :LinearLayout {
        var ll = LinearLayout(this)
        var lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        ll.layoutParams = lp
        ll.orientation = LinearLayout.VERTICAL
        when(count) {
            1 -> ll.setBackgroundResource( R.drawable.c1)
            2 -> ll.setBackgroundResource( R.drawable.c2)
            3 -> ll.setBackgroundResource( R.drawable.c3)
            4 -> ll.setBackgroundResource( R.drawable.c4)
            else -> ll.setBackgroundColor(0)
        }
        ll.background.setAlpha(120)
        return ll

    }

    private fun createCard() : CardView{
        val card = CardView(this)
        val layoutparams = LinearLayout.LayoutParams(
            300, 100
        ).apply{
            gravity = Gravity.CENTER
        }
        layoutparams.marginStart = 15
        layoutparams.marginEnd = 15
        layoutparams.topMargin = 10
        card.layoutParams = layoutparams
        card.radius = 10F
        return card
    }

    private fun createTextView(subtext : Boolean) : TextView {
        var t = TextView(this)
        val layout = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layout.setMargins(35, 5, 20, 0 )
        t.layoutParams = layout
        t.setPadding(30,0,10,0)
        if (subtext) {
            t.textSize = 12F
        }
        else {
            t.textSize = 20F
        }
        t.setTextColor(ContextCompat.getColor(this, R.color.black))
        return t

    }

    private fun adapter(s : String?, card : TextView) : TextView{
        card.text = s
        return card
    }

}