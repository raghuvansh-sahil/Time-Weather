package com.example.timeweather.data

import com.example.timeweather.model.Time
import com.example.timeweather.network.TimeApiService

interface TimeRepository {
    suspend fun getTime(
        timeKey : String,
        lat : String,
        long : String
    ) : Time
}

class NetworkTimeRepository(
    private val timeApiService: TimeApiService
) : TimeRepository {
    override suspend fun getTime(
        timeKey : String,
        lat : String,
        long : String
    ) : Time = timeApiService.getTime(timeKey = timeKey, lat = lat, lng = long)
}