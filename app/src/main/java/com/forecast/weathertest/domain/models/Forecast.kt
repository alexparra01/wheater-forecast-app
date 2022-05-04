package com.forecast.weathertest.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Forecast(
    val lat: Double,
    var lon: Double,
    var timezone: String,
    var timezone_offset: Int,
    var daily: List<DailyForecast>
)
