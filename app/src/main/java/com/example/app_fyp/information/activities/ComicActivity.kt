package com.example.app_fyp.information.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.view.*
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
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
    private lateinit var gn : ArrayList<String>
    private lateinit var rl : RelativeLayout


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic)
        data = intent.getSerializableExtra("COMIC") as Comic
        gn = intent.getStringArrayListExtra("GROUPNAMES") as ArrayList<String>
        val t : Toolbar? = findViewById(R.id.my_toolbar)
        t!!.setTitle(data.name)
        setSupportActionBar(t)
        rl = findViewById(R.id.rl)
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




        fillinData(data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.comic_menu, menu)


        val subm = menu!!.getItem(2).subMenu
        var ind : Int =  0
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
            R.id.addtogroup -> {
                true
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

    /*private fun makePopup(){

        // Inflate a custom view using layout inflater
        val sv = ScrollView(this)
        val view = LinearLayout(this)

        val popupWindow = PopupWindow(
            sv, // Custom view to show in popup window
            LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
            LinearLayout.LayoutParams.WRAP_CONTENT // Window height
        )


        view.setBackgroundColor(Color.WHITE)
        sv.addView(view)
        for ( i in gn) {
            val tv = TextView(this)
            val params : ViewGroup.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            tv.layoutParams = params
            tv.text = i
            tv.setPadding(5,2,5,2)
            tv.setOnClickListener {
                data.group = tv.text.toString()
                Toast.makeText(applicationContext,"${data.name} has been added to ${tv.text} ",Toast.LENGTH_SHORT).show()
                popupWindow.dismiss()
            }
            view.addView(tv)
        }
        // Initialize a new instance of popup window



        // Finally, show the popup window on app
        TransitionManager.beginDelayedTransition(rl)
        popupWindow.showAtLocation(
            rl, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )
        popupWindow.isFocusable = true
        popupWindow.update()

    }*/

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