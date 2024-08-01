package com.example.timeweather

import android.app.Application
import com.example.timeweather.data.AppContainer
import com.example.timeweather.data.DefaultAppContainer

class Application : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}