package com.forecast.weathertest.presentation.forecast

import android.util.Log
import android.view.View
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forecast.weathertest.domain.models.Forecast
import com.forecast.weathertest.domain.models.Location
import com.forecast.weathertest.domain.models.net.NetworkResult
import com.forecast.weathertest.domain.usecases.DateTransformationUseCase
import com.forecast.weathertest.domain.usecases.LocationUseCase
import com.forecast.weathertest.domain.usecases.WeatherForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "ForecastViewModel"

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val weatherForecastUseCase: WeatherForecastUseCase,
    private val dateTransformationUseCase: DateTransformationUseCase,
    private val locationUseCase: LocationUseCase
): ViewModel() {
    private lateinit var location: Location
    private lateinit var forecast: Forecast
    private var isLoading = mutableStateOf(true)
    private var hasError = mutableStateOf(false)
    private val _retrieveLastLocation get() = locationUseCase.retrieveStoredLocation().last()

    fun setLocation(location: Location?){
        if(location == null)
            this.location = _retrieveLastLocation
        else
           this.location = location
    }
    fun getLocation(): Location = location

    fun getForecast(): Forecast = forecast

    fun isLoading(): Boolean = isLoading.value

    fun hasError(): Boolean = hasError.value

    fun setHasError(value : Boolean){ this.hasError.value = value }

    fun retrieveWeather(){
        viewModelScope.launch {
            weatherForecastUseCase.retrieveWeatherForecast(location.lat,location.lon)
                .collect {
                    when(it){
                        is NetworkResult.Success -> {
                            forecast = it.data!!
                            isLoading.value = false
                        }
                        is NetworkResult.Loading -> {
                            isLoading.value = it.isLoading
                        }
                        is NetworkResult.Error -> {
                            Log.i(TAG, "Error: ${it.message}")
                            hasError.value = true
                        }
                    }
                }
        }
    }

    fun getDayOfWeek(timeStamp:Long): String {
        return dateTransformationUseCase.getDayOfWeekFromTimeStamp(timeStamp)
    }
    fun convertTimeStampToDateFormat(timeStamp:Long): String {
        return dateTransformationUseCase.convertTimeStampDateToDateFormat(timeStamp)
    }
}