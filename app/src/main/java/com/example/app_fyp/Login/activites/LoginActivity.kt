package com.example.app_fyp.Login.activites

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
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
    private var password: String? = null
    private val encrypter : Encrypter =
        Encrypter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reg = findViewById<View>(R.id.reg) as TextView
        login = findViewById<View>(R.id.log) as TextView
        email = findViewById<View>(R.id.email) as EditText
        password = encrypter.encrypt(findViewById<View>(R.id.password) as EditText, email.toString())

        reg!!.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        login!!.setOnClickListener {
            try {
                var intent = Intent(this@LoginActivity, WelcomeActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
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
