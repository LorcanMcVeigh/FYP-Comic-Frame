package com.example.app_fyp

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
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
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.lang.Exception
import java.net.URL

class SearchComicActivity : AppCompatActivity() {
    lateinit var rl : RelativeLayout
    var comicname : String = ""
    private val apikey : String = "4663e24bc7473eaef65158d7ec9d077342145a6c"
    private val query: String =
        "https://comicvine.gamespot.com/api/search/?api_key=${apikey}&format=json&resources=issue&query=%22${comicname}%22"
    private val useragent : String =
        "User-Agent':'Mozilla/5.0 (X11; Linux; Android 5.1.1; AndroidSDK built for x86 Build/LMY48X) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.0.0 Moblie Safari/537.36 GSA/4.1.29.1706998.x86"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchcomic)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        rl = findViewById(R.id.rl)

        val i = intent.getStringExtra("COMIC_NAME")

        Search(i)
    }

    private fun Search(name: String) {
        comicname = name.replace(" ", "%20")

        loadJson(query)
    }

    private fun loadJson(query: String) {
        query.httpGet().header(mapOf("User-Agent" to useragent)).response { _, respon, result ->
            var m: ArrayList<ComicResult> = ArrayList()
            if (respon.statusCode == 200) {
                try {
                    val jsondata = JsonParser().parse(result.get().toString(Charsets.UTF_8)).getAsJsonObject()
                    val res = jsondata["error"].toString().replace("\"", "")
                    if (res == "OK") {
                        val g = Gson()
                        Log.v("SAFSVDFVDFVDHASDDG", jsondata["results"].toString())
                        val itemtype = object : TypeToken<ArrayList<ComicResult>>() {}.type
                        m = Gson().fromJson<ArrayList<ComicResult>>(jsondata["results"], itemtype)
                        val b = m !is ArrayList<ComicResult>
                        Log.v("AWIBFLKJHBFSDKFKSAB", b.toString())
                        displayResults(m)

                    }
                } catch (e: Exception) {

                   Log.v("A;WIBFLKJHBFSDKFKDAS", e.printStackTrace().toString())
                }
            } else {
                Log.v("SEHGDSCVBERSAHDFBDSFS", result.component2().toString())
                Toast.makeText(
                    getApplicationContext(),
                    result.component2().toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun displayResults(content : ArrayList<ComicResult>) {
        Log.v("AWIBFLKJHBFSDKFKSAB", content[1].image.toString())
        val cont = content.iterator()
        var num = 0
        while (cont.hasNext()){
            val cr = cont.next()
            if (cr.image["thumb_url"].isNullOrEmpty()) {
                //Log.v("AWIBFLKJHBFSDKFKSAB", it.images.get("thumb_url").toString())
                val b = buildImageView(num)
                Glide.with(this).load(cr.image["thumb_url"].toString()).into(b)
                rl.addView(b)
            }
            num++
        }
    }

    private fun buildImageView(n: Int) : ImageView {
        val image = ImageView(this)
        val params = RelativeLayout.LayoutParams(300, 900)
        if (n % 2 == 0){
            params.setMargins(15,10,15,5)
        }
        image.layoutParams = params
        return image
    }
}