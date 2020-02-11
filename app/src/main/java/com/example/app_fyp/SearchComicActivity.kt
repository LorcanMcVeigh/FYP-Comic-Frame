package com.example.app_fyp

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.app_fyp.classes.APIResult
import com.example.app_fyp.classes.ComicResult
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.success
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.lang.Exception
import java.net.URL

class SearchComicActivity : AppCompatActivity() {
    lateinit var ll : LinearLayout
    lateinit var comicname : String
    private val apikey : String = "4663e24bc7473eaef65158d7ec9d077342145a6c"
    private val query: String =
        "https://comicvine.gamespot.com/api/search/?api_key=${{apikey}}&format=json&resources=issue&query=%22${{comicname}}"
    private val useragent : String =
        "User-Agent':'Mozilla/5.0 (X11; Linux; Android 5.1.1; AndroidSDK built for x86 Build/LMY48X) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.0.0 Moblie Safari/537.36 GSA/4.1.29.1706998.x86"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchcomic)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        ll = findViewById(R.id.ll)

        val i = intent.getStringExtra("COMIC_NAME")

        Search(i)
    }

    private fun Search(name: String) {
        comicname = name.replace(" ", "%20")
        val result = loadJson(query)
        if (result == null ) {
            Toast.makeText(getApplicationContext(),"No comic matched that name", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            displayResults(result.result)
        }


    }

    private fun loadJson(query: String): APIResult? {
        val m: APIResult?
        val ( _, respon, result) = query.httpGet().header(mapOf("User-Agent" to useragent)).response()
        if (respon.statusCode == 200) {
            try {
                val jsondata = result.get().toString()
                val gson = Gson()
                m = gson.fromJson(jsondata, APIResult::class.java)
                if (m.ecode != "ok") {
                    Toast.makeText(getApplicationContext(),"Recieved Error from comicvine.com", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show()
                return null
            }
        } else {
            Log.v("SEHGDSCVBERSAHDFBDSFS", result.component2().toString())
            Toast.makeText(getApplicationContext(),result.component2().toString(), Toast.LENGTH_SHORT).show()
            return null
        }
        return m
    }

    private fun displayResults(content : ArrayList<ComicResult>) {
        for (c in content) {
            val b = buildImageView()
            Glide.with(this).load(c.images.get("thumb_url")).into(b)
            ll.addView(b)
        }
    }

    private fun buildImageView() : ImageView {
        val image = ImageView(this)
        val params = LinearLayout.LayoutParams(300, 900)
        params.setMargins(15,10,15,5)
        image.layoutParams = params
        return image
    }
}