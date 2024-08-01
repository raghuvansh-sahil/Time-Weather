package com.example.timeweather.model.location

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val annotations : Annotations,
    val components: Components,
    val formatted: String,
    val geometry: Geometry
)