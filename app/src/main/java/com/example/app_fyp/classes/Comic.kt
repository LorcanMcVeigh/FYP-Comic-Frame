package com.example.app_fyp.classes

import java.io.Serializable

class Comic(var name : String, var image : String?, var issue : Int, val artist : String?, val hash : Int, var isFave : Boolean, var group : String?) : Serializable {
}
