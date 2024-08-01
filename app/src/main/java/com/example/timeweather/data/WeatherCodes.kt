package com.example.timeweather.data

import com.example.timeweather.R

object WeatherCodes {
    fun getColor(code : String) :  Int {
        return when (code) {
            "200", "201", "202", "210", "211", "212", "221", "230", "231", "232" -> R.drawable.thunderstorm
            "300", "301", "302", "310", "311", "312", "313", "314", "321" -> R.drawable.drizzle
            "500", "501", "502", "503", "504", "511", "520", "521", "522", "531" -> R.drawable.rain
            "600", "601", "602", "611", "612", "613", "615", "616", "620", "621", "622" -> R.drawable.snow
            "701", "711", "721", "731", "741", "751", "761", "762", "771", "781" -> R.drawable.fog
            "800" -> R.drawable.clear_sky
            "801", "802", "803", "804" -> R.drawable.cloudy_sky
            else -> R.drawable.clear_sky
        }
    }
}