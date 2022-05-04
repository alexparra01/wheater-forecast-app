package com.forecast.weathertest.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
