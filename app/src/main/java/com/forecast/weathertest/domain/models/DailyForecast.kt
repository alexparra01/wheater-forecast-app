package com.forecast.weathertest.domain.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DailyForecast (
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val moonrise: Long,
    val moonset: Long,
    val moon_phase: Double,
    val temp: Temperature,
    val feels_like: FeelsLike,
    val pressure: Int,
    val humidity: Int,
    val dew_point: Double,
    val wind_speed: Double,
    val wind_deg: Int,
    val wind_gust: Double,
    val weather: List<Weather>,
    val clouds: Int,
    val pop: Double,
    @Transient val rain: Double = 0.0,
    val uvi: Double
    )