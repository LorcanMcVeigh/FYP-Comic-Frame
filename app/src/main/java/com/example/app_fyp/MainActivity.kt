package com.example.app_fyp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private var reg: TextView? = null
    private var login: TextView? = null
    private var email: EditText? = null
    private var password: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        reg = findViewById<View>(R.id.reg) as TextView
        login = findViewById<View>(R.id.log) as TextView
        email = findViewById<View>(R.id.email) as EditText
        password = findViewById<View>(R.id.password) as EditText

        reg!!.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        login!!.setOnClickListener {
            try {
                login()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @Throws(IOException::class)
    private fun login(){

    }


}
