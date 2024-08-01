package com.example.timeweather.data

import com.example.timeweather.network.SearchApiService
import com.example.timeweather.network.TimeApiService
import com.example.timeweather.network.WeatherApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val searchRepository : SearchRepository
    val timeRepository : TimeRepository
    val weatherRepository : WeatherRepository
}

class DefaultAppContainer : AppContainer {
    private val locationsUrl = "https://api.opencagedata.com"
    private val locationsRetrofit : Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(locationsUrl)
        .build()
    private val locationsRetrofitService : SearchApiService by lazy {
        locationsRetrofit.create(SearchApiService::class.java)
    }
    override val searchRepository : SearchRepository by lazy {
        NetworkSearchRepository(locationsRetrofitService)
    }

    private val timeUrl = "https://api.timezonedb.com"
    private val timeRetrofit : Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(timeUrl)
        .build()
    private val timeRetrofitService : TimeApiService by lazy {
        timeRetrofit.create(TimeApiService::class.java)
    }
    override val timeRepository : TimeRepository by lazy {
        NetworkTimeRepository(timeRetrofitService)
    }

    private val weatherUrl = "https://api.openweathermap.org"
    private val weatherRetrofit : Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(weatherUrl)
        .build()
    private val weatherRetrofitService : WeatherApiService by lazy {
        weatherRetrofit.create(WeatherApiService::class.java)
    }
    override val weatherRepository : WeatherRepository by lazy {
        NetworkWeatherRepository(weatherRetrofitService)
    }
}