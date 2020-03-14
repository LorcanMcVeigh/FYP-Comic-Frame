package com.example.app_fyp.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.transition.*
import android.view.*
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_fyp.information.activities.AddComicActivity
import com.example.app_fyp.R
import com.example.app_fyp.classes.Comic
import com.example.app_fyp.ui.home.FaveComicAdapter
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HomeActivity : AppCompatActivity() {
    private var ADD_COMIC_REQUEST = 1
    private var UPDATE_COMIC_ISSSUE = 2
    private var DELETE_COMIC = 3
    private var lst : ArrayList<Comic> = ArrayList()
    private var favelst : ArrayList<Comic> = ArrayList()
    private var grouplst : HashMap<String, ArrayList<Comic>> = HashMap()
    private lateinit var root_layout : ScrollView
    private lateinit var sv : LinearLayout
    private lateinit var fab : View
    //private lateinit var load : RelativeLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            sharedElementReenterTransition
            sharedElementReturnTransition
            allowEnterTransitionOverlap
            allowReturnTransitionOverlap
        }
        setContentView(R.layout.activity_home)
        val t : Toolbar? = findViewById(R.id.my_toolbar)
        t!!.setOnClickListener { displayContent(grouplst) }
        setSupportActionBar(t)
        // connect and recieve the contents of the database for this user
        // load them into objects and display objects#
        //load = findViewById(R.id.loadingPanel)
        //load.bringToFront()
        root_layout = findViewById(R.id.root_layout)
        sv = findViewById(R.id.ll)
        fab = findViewById(R.id.fab)
        fab.setOnClickListener {
            val popup = PopupMenu(this, fab)

            val inflater = popup.menuInflater
            inflater.inflate(R.menu.add_menu, popup.menu)
            popup.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.add_comic -> {
                        val intent = Intent(this@HomeActivity, AddComicActivity::class.java)
                        startActivityForResult(intent, ADD_COMIC_REQUEST)
                    }
                    R.id.makegroup -> {
                        makePopup()
                    }

                }
                true
            }
            popup.show()

        }
        loadData()


    }

    override fun onRestart() {
        super.onRestart()
        displayContent(grouplst)
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        when (item.itemId) {
            R.id.search -> {
                makeSearchPopup()
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
                    comic = Comic("", "",0, "", 0, false, null)
                }
                if (resultCode == Activity.RESULT_OK){
                    if (comic.group != "" && comic.group != null){
                        val l = grouplst.get(comic.group.toString())
                        if (l!!.find{ it.hash == comic.hash} == null ) {
                            l.add(comic)
                            grouplst.put(comic.group.toString(), l)
                        }

                    } else {
                        Toast.makeText(
                            getApplicationContext(),
                            "Unable to update Comic",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else if (resultCode == Activity.RESULT_CANCELED){
                    if (comic.name == "") {
                        Toast.makeText(getApplicationContext(),"Unable to update Comic",Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    Toast.makeText(getApplicationContext(),"Comic list unable to updated",Toast.LENGTH_SHORT).show();
                }
            }

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

        //displayContent()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        return true
    }

    private fun makeSearchPopup(){
        val inflater:LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Inflate a custom view using layout inflater
        val view = inflater.inflate(R.layout.search_window,null)

        // Initialize a new instance of popup window
        val popupWindow = PopupWindow(
            view, // Custom view to show in popup window
            LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
            LinearLayout.LayoutParams.WRAP_CONTENT // Window height
        )

        val tv = view.findViewById<EditText>(R.id.searchname)
        val buttonPopup = view.findViewById<Button>(R.id.search)


        // Set a click listener for popup's button widget
        buttonPopup.setOnClickListener{
            // Dismiss the popup window
            if (tv.text.toString() != ""){
                //load.visibility = View.VISIBLE
                val s = HashMap<String, ArrayList<Comic>>()
                for ((i,v) in grouplst){
                    val f : ArrayList<Comic> = ArrayList(v.filter{ it.name.toUpperCase().contains(tv.text.toString().toUpperCase())})
                    if (f.size > 0){
                        s.put(i, f)
                    }
                }
                displayContent(s)
            }
            popupWindow.dismiss()
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
                Toast.makeText(applicationContext,"Group Added!",Toast.LENGTH_SHORT).show()
            }
            popupWindow.dismiss()
            displayContent(grouplst)
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

    private fun displayContent( groups :  HashMap<String, ArrayList<Comic>>,sort : String = "name"){

        sv.removeAllViewsInLayout()
        when(sort){
            "name" -> lst.sortedWith(compareBy{ it.name })
            "artist" -> lst.sortedWith(compareBy{ it.artist })
            else -> lst.sortedWith(compareBy{ it.name })
        }
        val gn = groups.keys
        val groupnames = ArrayList<String>()
        for ( i in gn){
            groupnames.add(i)
        }
        for ((g,v) in groups) {
            val grc = buildRecycleView(v, groupnames)

            val tv = buildTextView(g)
            sv.addView(tv)
            sv.addView(grc)

        }
        //load.visibility = View.GONE

    }

    private fun buildTextView(name : String) : TextView {
        val tv = TextView(this)
        val params : ViewGroup.LayoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val margin = ViewGroup.MarginLayoutParams(params)
        margin.bottomMargin = 20
        tv.layoutParams = margin
        tv.setPadding(40, 0,30,5)
        tv.setTextColor(Color.parseColor("#000000"))
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
            lst.add(Comic("X-men Dark Phoenix", "d", 2, "joe", 1, false, "Default" ))
            favelst.add(Comic("The Flash and Green Lantern", "d", 7, "joe",2, true, "Favorite"))
            favelst.add(Comic("Katy Keene", "d", 9, "joe",3, true, "Favorite"))
            lst.add(Comic("Great Comics", "d", 1, "joe",4, false,"Default" ))

            grouplst.put("Favorite", favelst)
            grouplst.put("Default", lst)

            displayContent(grouplst)

        }

    }

}
