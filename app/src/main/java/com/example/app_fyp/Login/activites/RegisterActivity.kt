package com.example.app_fyp.Login.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.app_fyp.ErrorActivity
import com.example.app_fyp.Login.Encrypter
import com.example.app_fyp.ui.activities.MainActivity
import com.example.app_fyp.R
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import java.io.IOException
import kotlin.Exception

class RegisterActivity : AppCompatActivity() {
    private val url: String = "https://10.0.2.2:8080/"
    private lateinit var name: EditText
    private lateinit var regbtn: Button
    private lateinit var logbtn: Button
    private lateinit var email : EditText
    private lateinit var password: String
    private val encrypter : Encrypter = Encrypter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        getViews()

        setListeners()
    }

    private fun getViews() {
        name = findViewById<View>(R.id.name) as EditText
        regbtn = findViewById<View>(R.id.regbtn) as Button
        logbtn = findViewById<View>(R.id.logbtn) as Button
        email = findViewById<View>(R.id.email) as EditText

        password = encrypter.encrypt(findViewById<View>(R.id.password) as EditText, email.toString())
    }


    private fun setListeners() {
        logbtn!!.setOnClickListener {
            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }
        regbtn!!.setOnClickListener {
            try {
                register()
            } catch (e: IOException) {
                getErrorMessage()
            }
        }
    }

    @Throws(IOException::class)
    private fun register(){
        val data = Gson().toJson(listOf(
            "type" to "register",
            "name" to name!!,
            "username" to email!!,
            "password" to password!!
        ))

        try {
            val (request, response, result) = Fuel.post(url).body(data).response()
            if (response.statusCode != 200) {
                val tv : TextView = findViewById(R.id.textfield)
                tv.setText("Email already in use by another user")
            } else if ( result.component2() != null ) {
                getErrorMessage()
            }

        } catch (e : Exception) {
            getErrorMessage()
        }
    }


    private fun getErrorMessage() {
        val intent = Intent(this@RegisterActivity, ErrorActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        this.finish()
    }


}