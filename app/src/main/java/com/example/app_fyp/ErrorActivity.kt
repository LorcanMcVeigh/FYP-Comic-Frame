package com.example.app_fyp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.app_fyp.ui.activities.MainActivity

class ErrorActivity : AppCompatActivity() {
    private var logbtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)

        logbtn = findViewById(R.id.logbtn)

        logbtn!!.setOnClickListener {
            val intent = Intent(this@ErrorActivity, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
}