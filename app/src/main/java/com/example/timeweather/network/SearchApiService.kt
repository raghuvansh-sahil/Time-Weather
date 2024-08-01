package com.example.timeweather.network


import com.example.timeweather.model.location.Results
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {
    @GET("/geocode/v1/json")
    suspend fun getLocations(
        @Query("q") prefix : String,
        @Query("key") searchKey : String
    ) : Results
}