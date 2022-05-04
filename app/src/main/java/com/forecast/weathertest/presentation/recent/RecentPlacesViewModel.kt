package com.forecast.weathertest.presentation.recent

import androidx.lifecycle.ViewModel
import com.forecast.weathertest.domain.models.Location
import com.forecast.weathertest.domain.usecases.LocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RecentPlacesViewModel @Inject constructor(
    private val locationUseCase: LocationUseCase
): ViewModel() {
    fun retrieveSavedLocation(): List<Location> {
        return locationUseCase.retrieveStoredLocation()
    }

    fun selectedLocationToBeRemoved(location: Location){
        locationUseCase.selectedLocationToBeRemoved(location)
    }

    fun unSelectedLocationToBeRemoved(location: Location){
        locationUseCase.unSelectedLocationToBeRemoved(location)
    }

    fun deleteLocations(){
        locationUseCase.deleteLocations()
    }

    fun isThereAnySelectedItem() = locationUseCase.getSelectedList().isNotEmpty()
}