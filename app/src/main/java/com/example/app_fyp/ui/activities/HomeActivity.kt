package com.example.app_fyp.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.transition.Scene
import android.transition.TransitionManager
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_fyp.information.activities.AddComicActivity
import com.example.app_fyp.R
import com.example.app_fyp.classes.Comic
import com.example.app_fyp.ui.home.ComicAdapter
import com.example.app_fyp.ui.home.FaveComicAdapter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HomeActivity : AppCompatActivity() {
    private var ADD_COMIC_REQUEST = 1
    private var UPDATE_COMIC_ISSSUE = 2
    private var DELETE_COMIC = 3
    private var lst : ArrayList<Comic> = ArrayList()
    private var favelst : ArrayList<Comic> = ArrayList()
    private var grouplst : HashMap<String, ArrayList<Comic>> = HashMap()
    private lateinit var root_layout : LinearLayout
    private lateinit var sv : LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        // connect and recieve the contents of the database for this user
        // load them into objects and display objects
        root_layout = findViewById(R.id.root_layout)
        sv = findViewById(R.id.ll)
        loadData()


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
            R.id.makegroup -> {
                makePopup()
            }

            else -> {
                super.onOptionsItemSelected(item)
            }

        }
        return true
    }

    @Throws(KotlinNullPointerException::class, Exception::class)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var comic : Comic
        when ( requestCode ) {
            ADD_COMIC_REQUEST -> { if (resultCode == RESULT_OK) {
                comic = data!!.getSerializableExtra("EXTRA_COMIC") as Comic
                lst.add(comic)
            } else {
                Toast.makeText(getApplicationContext(),"Unable to add Comic", Toast.LENGTH_SHORT).show();
            } }
            UPDATE_COMIC_ISSSUE -> {
                try {
                    comic = data!!.getSerializableExtra("COMIC") as Comic
                } catch (e: KotlinNullPointerException){
                    comic = Comic("", "",ArrayList(), "", 0, false, null)
                }
                if (resultCode == Activity.RESULT_OK){
                    comic = data!!.getSerializableExtra("COMIC") as Comic
                    try {
                        lst.remove(lst.find { it.hash == comic.hash })
                    } catch (e:Exception){
                        favelst.remove(favelst.find{ it.hash == comic.hash})
                    }
                    if (comic.group != "" && comic.group != null){
                        grouplst.get(comic.group.toString())!!.add(comic)
                    } else {
                        lst.add(comic)
                    }

                } else if (resultCode == Activity.RESULT_CANCELED){
                    if (comic.name == "") {
                        Toast.makeText(getApplicationContext(),"Unable to update Comic",Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                Toast.makeText(getApplicationContext(),"Comic list unable to updated",Toast.LENGTH_SHORT).show();
            }}

            DELETE_COMIC -> {
                comic = data!!.getSerializableExtra("COMIC") as Comic
                if (resultCode == Activity.RESULT_OK){
                    if (comic.isFave) {
                        favelst.remove(favelst.find{ it.hash == comic.hash})
                    } else {
                        lst.remove(lst.find{ it.hash == comic.hash})
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Unable to delete Comic",Toast.LENGTH_SHORT).show()
                }
            }
        }

        displayContent()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    private fun makePopup(){
        val inflater:LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Inflate a custom view using layout inflater
        val view = inflater.inflate(R.layout.group_window,null)

        // Initialize a new instance of popup window
        val popupWindow = PopupWindow(
            view, // Custom view to show in popup window
            LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
            LinearLayout.LayoutParams.WRAP_CONTENT // Window height
        )

        val tv = view.findViewById<EditText>(R.id.newgroupname)
        val buttonPopup = view.findViewById<Button>(R.id.addgroup)


        // Set a click listener for popup's button widget
        buttonPopup.setOnClickListener{
            // Dismiss the popup window
            if (tv.text.toString() != ""){
                grouplst.put(tv.text.toString(), ArrayList<Comic>())
                Toast.makeText(applicationContext,"Group ${tv.text} added",Toast.LENGTH_SHORT).show()
            }
            popupWindow.dismiss()
            displayContent()
        }

        // Finally, show the popup window on app
        TransitionManager.beginDelayedTransition(root_layout)
        popupWindow.showAtLocation(
            root_layout, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )
        popupWindow.isFocusable = true
        popupWindow.update()

    }

    private fun displayContent(sort : String = "name"){
        sv.removeAllViewsInLayout()
        when(sort){
            "name" -> lst.sortedWith(compareBy{ it.name })
            "artist" -> lst.sortedWith(compareBy{ it.artist })
            else -> lst.sortedWith(compareBy{ it.name })
        }
        val gn = grouplst.keys
        val groupnames = ArrayList<String>()
        for ( i in gn){
            groupnames.add(i)
        }
        for ((g,v) in grouplst) {
            val grc = buildRecycleView(v, groupnames)

            val tv = buildTextView(g)
            sv.addView(tv)
            sv.addView(grc)

        }

        val rc = RecyclerView(this)//findViewById<RecyclerView>(R.id.recyclerview)
        rc.layoutManager = LinearLayoutManager(this)
        val tv = buildTextView("collection")
        rc.adapter = ComicAdapter(lst, this, groupnames)
        sv.addView(tv)
        sv.addView(rc)
    }

    private fun buildTextView(name : String) : TextView {
        val tv = TextView(this)
        val params : ViewGroup.LayoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        tv.layoutParams = params
        tv.setPadding(40, 0,30,5)
        tv.setTextColor(Color.parseColor("#FFFFFF"))
        tv.textSize = 20f
        tv.text = name
        return tv
    }

    private fun buildRecycleView(data : ArrayList<Comic>, groupnames : ArrayList<String>) : RecyclerView{
        val rc = RecyclerView(this)
        val params : ViewGroup.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        val margin = ViewGroup.MarginLayoutParams(params)

        margin.bottomMargin = 30
        rc.layoutParams = margin

        rc.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rc.adapter = FaveComicAdapter(data, this, groupnames )

        return rc
    }

    private fun loadData() {
        // parse data (somehow Gson?)
        // make objects imageview, textview,  etc
        // load the data into comic objects
        // val view = ArrayList<CardView>()
        if (lst.size < 1) {
            val a = ArrayList<Int>()
            a.add(2)
            lst.add(Comic("X-men Dark Phoenix", "d", a, "joe", 1, false, null ))
            favelst.add(Comic("The Flash and Green Lantern", "d", a, "joe",2, true, "favorite"))
            favelst.add(Comic("Katy Keene", "d", a, "joe",3, true, "favorite"))
            a.add(3)
            a.add(5)
            a.add(4)
            a.add(6)
            a.add(7)
            a.add(8)
            a.add(9)
            lst.add(Comic("Great Comics", "d", a, "joe",4, false,null ))

            grouplst.put("Favorite", favelst)

            displayContent()

        }

    }

}
