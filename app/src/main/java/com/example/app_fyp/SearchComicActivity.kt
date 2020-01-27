package com.example.app_fyp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson

class SearchComicActivity : AppCompatActivity(){
    private val query : String = "https://comicvine.gamespot.com/api/search/?api_key=4663e24bc7473eaef65158d7ec9d077342145a6c&format=json&resources=issue&query=%22"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        val i = intent.getStringExtra("COMIC_NAME")

        Search(i)
    }

    private fun Search(name : String) {
        val n = name.replace(" ", "%20")
        val r = query+n+"%22"
        val(request, respon, result) = r.httpGet().header().response()
        if (respon.statusCode == 200) {
            val data = result.component1().toString()
            try {
                //data['results']
                /*val gson = Gson()
                val m = gson.fromJson(data, HashMap::class.java)
                val ex : ArrayList<String> = m.get("results")
                val c = ex.size
                ex?.let {
                    for ((i,j) in it){

                    }
                }*/



            } catch (e:Exception){
                // print something to the screen
            }
        }
    }
}