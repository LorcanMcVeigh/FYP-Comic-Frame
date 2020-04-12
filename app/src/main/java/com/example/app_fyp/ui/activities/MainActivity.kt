package com.example.app_fyp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.app_fyp.Login.activites.LoginActivity
import java.io.IOException
import com.example.app_fyp.R

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
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        startActivity(intent)

    }
}