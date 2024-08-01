package com.example.timeweather.model.location

import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    val lat: String,
    val lng: String
)