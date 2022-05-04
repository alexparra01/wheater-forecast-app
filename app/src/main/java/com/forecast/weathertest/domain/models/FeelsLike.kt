package com.forecast.weathertest.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeelsLike(
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double
)
