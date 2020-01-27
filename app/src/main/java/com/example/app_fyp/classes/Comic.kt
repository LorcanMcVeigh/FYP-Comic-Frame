package com.example.app_fyp.classes

import java.io.Serializable

class Comic(var name : String, var image : String?, var issue : ArrayList<Int>?, val artist : String?, val hash : Int) : Serializable {
}
