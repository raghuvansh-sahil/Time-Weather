package com.example.timeweather.network

import com.example.timeweather.model.weather.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("/data/2.5/weather")
    suspend fun getWeatherData(
        @Query("lat") lat : String,
        @Query("lon") lon : String,
        @Query("appid") weatherKey : String,
        @Query("units") unit : String = "metric"
    ) : WeatherData
}