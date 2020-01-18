package com.example.app_fyp.classes

import java.io.Serializable

class Comic(val name : String, var image : String?, val issue : ArrayList<Int>?, val artist : String?) : Serializable {
}
