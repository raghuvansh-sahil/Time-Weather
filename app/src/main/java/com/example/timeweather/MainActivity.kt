package com.example.timeweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.timeweather.ui.theme.TimeWeatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val searchKey = BuildConfig.searchKey
        setContent {
            TimeWeatherTheme {
                TimeWeatherApp(searchKey = searchKey)
            }
        }
    }
}