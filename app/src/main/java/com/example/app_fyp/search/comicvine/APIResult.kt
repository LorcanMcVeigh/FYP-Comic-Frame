package com.example.app_fyp.search.comicvine

import com.example.app_fyp.search.comicvine.ComicResult

class APIResult(val ecode : String, val limit : Int, val noofpageresult :Int, val nototalpage : Int,
                val offset : Int, val result: ArrayList<ComicResult>) {

}