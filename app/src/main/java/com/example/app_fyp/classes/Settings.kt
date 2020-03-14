package com.example.app_fyp.classes


import com.example.app_fyp.R
import java.io.Serializable

class Settings : Serializable{
    var textsize : Int = 12
    var titlesize : Int = 20
    var textcolor : String = "black"
    var titlecolor : String = "black"
    var theme : Int = R.style.AppTheme
    var iconcolor : String = "black"

}