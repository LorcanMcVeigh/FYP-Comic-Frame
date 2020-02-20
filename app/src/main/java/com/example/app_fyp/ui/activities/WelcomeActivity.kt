package com.example.app_fyp.ui.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.app_fyp.R

class WelcomeActivity : AppCompatActivity() {
    lateinit var t : TextView
    val font_list :ArrayList<String> = arrayListOf("shakapow", "shakapowhollow", "shakapowupright",
        "shakapowuprighthollow", "theamazingspiderman", "thinksmart")

    val color_list : ArrayList<String> = arrayListOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        t = findViewById(R.id.tv1)
        val max = font_list.size-1
        val face : Typeface?
        val r = font_list[(0..max).random()]
        when (r) {
            "shakepow" -> { face = ResourcesCompat.getFont(this, R.font.shakapow)}
            "shakepowhollow" -> { face = ResourcesCompat.getFont(this, R.font.shakapowhollow)}
            "shakepowupright" -> { face = ResourcesCompat.getFont(this, R.font.shakapowupright)}
            "shakepowuprighthollow" -> { face = ResourcesCompat.getFont(this, R.font.shakapowuprighthollow)}
            "theamazingspiderman" -> { face = ResourcesCompat.getFont(this, R.font.theamazingspiderman)}
            "thinksmart" -> { face = ResourcesCompat.getFont(this, R.font.thinksmart)}
            else -> { face = Typeface.SANS_SERIF }
        }
        t.setTypeface(face)
        Handler().postDelayed({
                val i = Intent(this@WelcomeActivity, HomeActivity::class.java)
                startActivity(i)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()

        }, 4000)
        Log.v("TIEMIDFIWJ", "HEREE")
    }
}