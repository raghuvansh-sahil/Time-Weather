package com.example.timeweather.data

import com.example.timeweather.model.weather.WeatherData
import com.example.timeweather.network.WeatherApiService

interface WeatherRepository {
    suspend fun getWeather(
        lat : String,
        long : String,
        weatherKey : String
    ) : WeatherData
}

class NetworkWeatherRepository(
    private val weatherApiService: WeatherApiService
) : WeatherRepository {
    override suspend fun getWeather(
        lat : String,
        long : String,
        weatherKey : String
    ) : WeatherData = weatherApiService.getWeatherData(lat = lat, lon = long, weatherKey = weatherKey)
}