package com.forecast.weathertest.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Temperature(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)
