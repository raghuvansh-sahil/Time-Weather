package com.example.timeweather.data

import com.example.timeweather.model.location.Results
import com.example.timeweather.network.SearchApiService

interface SearchRepository {
    suspend fun getLocations(
        prefix : String,
        searchKey : String
    ) : Results
}

class NetworkSearchRepository(
    private val searchApiService: SearchApiService
) : SearchRepository {
    override suspend fun getLocations(
        prefix : String,
        searchKey : String
    ) : Results = searchApiService.getLocations(prefix = prefix, searchKey = searchKey)
}