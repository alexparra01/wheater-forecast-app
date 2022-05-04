package com.forecast.weathertest.domain.usecases

import com.forecast.weathertest.data.util.Constants
import com.forecast.weathertest.domain.models.Forecast
import com.forecast.weathertest.domain.models.net.NetworkResult
import com.forecast.weathertest.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface WeatherForecastUseCase {
    suspend fun retrieveWeatherForecast(lat: Double, lon:Double): Flow<NetworkResult<Forecast>>
}

class WeatherForecastUseCaseImpl @Inject constructor(
    private val weatherRepository: WeatherRepository
): WeatherForecastUseCase {
    override suspend fun retrieveWeatherForecast(lat: Double, lon: Double): Flow<NetworkResult<Forecast>> {
        return flow {
            emit(NetworkResult.Loading(null,true))
            val response = weatherRepository.getForecast(lat, lon, Constants.EXCLUDE, Constants.APP_ID, Constants.UNITS)
            if(response.isSuccessful){
                emit(NetworkResult.Success(response.body()))
                emit(NetworkResult.Loading(null,false))
            } else {
                val errorMsg = response.errorBody()?.string()
                response.errorBody()?.close()
                emit(NetworkResult.Error(errorMsg!!))
                emit(NetworkResult.Loading(null,false))
            }
        }.flowOn(Dispatchers.IO)
    }
}