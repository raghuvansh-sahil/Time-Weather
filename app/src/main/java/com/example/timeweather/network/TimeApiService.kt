package com.example.timeweather.network

import com.example.timeweather.model.Time
import retrofit2.http.GET
import retrofit2.http.Query

interface TimeApiService {
    @GET("/v2.1/get-time-zone")
    suspend fun getTime(
        @Query("key") timeKey : String,
        @Query("format") format : String = "json",
        @Query("by") method : String = "position",
        @Query("lat") lat: String,
        @Query("lng") lng : String
    ) : Time
}