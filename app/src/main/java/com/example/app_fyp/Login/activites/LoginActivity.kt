package com.example.app_fyp.Login.activites

import android.content.Intent
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.app_fyp.ErrorActivity
import com.example.app_fyp.ui.activities.HomeActivity
import com.example.app_fyp.Login.Encrypter
import com.example.app_fyp.R
import com.example.app_fyp.ui.activities.WelcomeActivity
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    private val url: String = ""
    private var reg: TextView? = null
    private var login: TextView? = null
    private var email: EditText? = null
    private var password: EditText? = null
    private lateinit var ll : LinearLayout
    private val encrypter : Encrypter =
        Encrypter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val e = Explode()
        e.addTarget(R.id.email)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            exitTransition = Fade()
            //exitTransition.addTarget(R.id.email)
        }
        setContentView(R.layout.activity_main)

        reg = findViewById<View>(R.id.reg) as TextView
        login = findViewById<View>(R.id.log) as TextView
        email = findViewById<View>(R.id.email) as EditText
        //email!!.getBackground().setColorFilter( Color)
        password = findViewById<View>(R.id.password) as EditText
        ll = findViewById(R.id.ll)

        ll.setOnClickListener {
            email!!.layoutParams.width = 200
            password!!.layoutParams.width = 200
        }

        email!!.setOnClickListener {
            password!!.layoutParams.width = 200
            email!!.layoutParams.width = 10//LinearLayout.LayoutParams.MATCH_PARENT
        }

        password!!.setOnClickListener {
            email!!.layoutParams.width = 200
            password!!.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        }

        reg!!.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        login!!.setOnClickListener {
            email!!.layoutParams.width = 200
            password!!.layoutParams.width = 200
            try {
                //val ps = Encrypter().encrypt(password, email!!.text.toString())
                val intent = Intent(this@LoginActivity, WelcomeActivity::class.java)
                startActivity(intent)
                //overridePendingTransition(R.transition.slide_in_right, R.transition.slide_out_right)
                this.finish()
                //login()
            } catch (e: IOException) {
                getErrorMessage()
            }
        }
    }

    @Throws(IOException::class)
    private fun login(){
        val data = Gson().toJson(listOf(
            "type" to "login",
            "username" to email!!,
            "password" to password!!
        ))

        try {
            val (request, response, result) = Fuel.post(url).body(data).response()
            if (response.statusCode != 200 ) {
                val tv: TextView = findViewById(R.id.textfield)
                tv.setText("Username or password incorrect")
            }else if (response.statusCode == 200 ){
                var intent = Intent(this@LoginActivity, WelcomeActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                this.finish()
            } else {
                getErrorMessage()
            }

        } catch (e : Exception) {

        }
    }
    private fun getErrorMessage(){
        var intent = Intent(this@LoginActivity, ErrorActivity::class.java)
        startActivity(intent)

        this.finish()
    }


}
