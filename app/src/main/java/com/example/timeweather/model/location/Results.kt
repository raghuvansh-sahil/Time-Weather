package com.example.timeweather.model.location

import kotlinx.serialization.Serializable

@Serializable
data class Results(
    val results : List<Result>
)