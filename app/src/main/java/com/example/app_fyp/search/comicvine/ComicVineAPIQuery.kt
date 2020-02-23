package com.example.app_fyp.search.comicvine

import android.util.Log
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.lang.Exception

class ComicVineAPIQuery(val comicname: String) {
    lateinit var data : ArrayList<ComicResult>
    private val apikey : String = "4663e24bc7473eaef65158d7ec9d077342145a6c"
    val search: String =
        "https://comicvine.gamespot.com/api/search/?api_key=${apikey}&format=json&resources=issue&query=%22${comicname}%22"
    private val useragent : String =
        "User-Agent':'Mozilla/5.0 (X11; Linux; Android 5.1.1; AndroidSDK built for x86 Build/LMY48X) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.0.0 Moblie Safari/537.36 GSA/4.1.29.1706998.x86"

    init {
        loadJson()
    }

    fun loadJson() {
        data = ArrayList()
        search.httpGet().header(mapOf("User-Agent" to useragent)).response { _, response, result ->
            var m: ArrayList<ComicResult> = ArrayList()
            if (response.statusCode == 200) {
                try {
                    val jsondata = JsonParser().parse(result.get().toString(Charsets.UTF_8)).getAsJsonObject()
                    val res = jsondata["error"].toString().replace("\"", "")
                    if (res == "OK") {
                        val g = Gson()
                        val itemtype = object : TypeToken<ArrayList<ComicResult>>() {}.type
                        data = Gson().fromJson<ArrayList<ComicResult>>(jsondata["results"], itemtype)
                        Log.v("ANKKNVVKERKRAEKP", "HERERERERE")
                    }
                } catch (e: Exception) {
                    data = ArrayList()
                    Log.v("ANKKNVVKERKRAEKP", "HERERERERE")

                }
            } else {
                data = ArrayList()
            }
        }
    }
}