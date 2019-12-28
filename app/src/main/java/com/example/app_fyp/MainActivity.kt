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
        showComics()
    }

    fun showComics() {
        var intent = Intent(this@MainActivity, HomeActivity::class.java)
        startActivity(intent)
    }
}
