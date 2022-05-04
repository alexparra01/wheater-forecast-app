package com.forecast.weathertest.presentation

import androidx.lifecycle.ViewModel
import com.forecast.weathertest.domain.usecases.LocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val locationUseCase: LocationUseCase
): ViewModel() {
    private val _hasStoredPlaces get()  = locationUseCase.retrieveStoredLocation().isNotEmpty()

    fun handleStartNavigation(searchCallBack:()->Unit, forecastCallBack:()->Unit){
        if(_hasStoredPlaces){
            forecastCallBack.invoke()
        }else {
            searchCallBack.invoke()
        }
    }
}