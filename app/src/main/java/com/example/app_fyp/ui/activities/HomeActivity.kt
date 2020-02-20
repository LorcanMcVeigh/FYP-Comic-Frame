package com.example.app_fyp.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.transition.Scene
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_fyp.information.activities.AddComicActivity
import com.example.app_fyp.R
import com.example.app_fyp.classes.Comic
import com.example.app_fyp.ui.home.ComicAdapter
import com.example.app_fyp.ui.home.FaveComicAdapter
import java.util.*

class HomeActivity : AppCompatActivity() {
    private var ADD_COMIC_REQUEST = 1
    private var UPDATE_COMIC_ISSSUE = 2
    private var DELETE_COMIC = 3
    private var lst : ArrayList<Comic> = ArrayList()
    private var favelst : ArrayList<Comic> = ArrayList()


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
                    comic = Comic("", "",ArrayList(), "", 0, false)
                }
                if (resultCode == Activity.RESULT_OK){
                    comic = data!!.getSerializableExtra("COMIC") as Comic
                    try {
                        lst.remove(lst.find { it.hash == comic!!.hash })
                    } catch (e:Exception){
                        favelst.remove(favelst.find{ it.hash == comic!!.hash})
                    }
                    if (comic.isFave){
                        favelst.add(comic!!)
                    } else {
                        lst.add(comic!!)
                    }

                } else if (resultCode == Activity.RESULT_CANCELED){
                    if (comic!!.name == "") {
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
                        favelst.remove(favelst.find{ it.hash == comic!!.hash})
                    } else {
                        lst.remove(lst.find{ it.hash == comic!!.hash})
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

    private fun displayContent(sort : String = "name"){
        loadData()

        when(sort){
            "name" -> lst.sortedWith(compareBy{ it.name })
            "artist" -> lst.sortedWith(compareBy{ it.artist })
            else -> lst.sortedWith(compareBy{ it.name })
        }

        val rcf = findViewById<RecyclerView>(R.id.recyclerviewfaves)
        rcf.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rcf.adapter = FaveComicAdapter(favelst, this)

        val rc = findViewById<RecyclerView>(R.id.recyclerview)
        rc.layoutManager = LinearLayoutManager(this)
        rc.adapter = ComicAdapter(lst, this)

    }

    private fun loadData() {
        // parse data (somehow Gson?)
        // make objects imageview, textview,  etc
        // load the data into comic objects
        // val view = ArrayList<CardView>()
        if (lst.size < 1) {
            val a = ArrayList<Int>()
            a.add(2)
            lst.add(Comic("X-men Dark Phoenix", "d", a, "joe", 1, false ))
            favelst.add(Comic("The Flash and Green Lantern", "d", a, "joe",2, true))
            favelst.add(Comic("Katy Keene", "d", a, "joe",3, true))
            a.add(3)
            a.add(5)
            a.add(4)
            a.add(6)
            a.add(7)
            a.add(8)
            a.add(9)
            lst.add(Comic("Great Comics", "d", a, "joe",4, false ))

        }

    }

}
