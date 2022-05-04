package com.forecast.weathertest.data.source.net

import com.forecast.weathertest.domain.models.Forecast
import retrofit2.Response
import javax.inject.Inject

interface NetDataSource {
    suspend fun forecast(lat: Double, lon:Double,exclude: String, appId: String, units: String): Response<Forecast>
}

class NetDataSourceImpl @Inject constructor(private val weatherRestApi: WeatherRestApi): NetDataSource {
    override suspend fun forecast(lat: Double, lon:Double,exclude: String, appId: String, units: String): Response<Forecast> {
        return weatherRestApi.getForecast(lat.toString(), lon.toString(), exclude, appId, units)
    }
}