package com.forecast.weathertest.data.source.net

import com.forecast.weathertest.data.util.Constants
import com.forecast.weathertest.domain.models.Forecast
import retrofit2.Response
import retrofit2.http.*

interface WeatherRestApi {
    @Headers("Accept: application/json")
    @GET(Constants.FORECAST_URL)
    suspend fun getForecast(@Query("lat") lat: String, @Query("lon") lon: String, @Query("exclude") exclude: String, @Query("appid") appId: String, @Query("units") units: String): Response<Forecast>

}