package com.forecast.weathertest.domain.usecases

import com.forecast.weathertest.domain.models.Location
import com.forecast.weathertest.domain.repository.WeatherRepository
import javax.inject.Inject

interface LocationUseCase {
    fun saveRecentLocation(location: Location)
    fun retrieveStoredLocation(): List<Location>
    fun selectedLocationToBeRemoved(location: Location)
    fun unSelectedLocationToBeRemoved(location: Location)
    fun getSelectedList():List<Location>
    fun deleteLocations()
}

class LocationUseCaseImpl @Inject constructor(
    private val weatherRepository: WeatherRepository
) : LocationUseCase {

    private var selectedItemsList = mutableListOf<Location>()

    override fun saveRecentLocation(location: Location) {
        weatherRepository.addToLocationList(location)
    }

    override fun retrieveStoredLocation(): List<Location> {
        return weatherRepository.getLastStoredLocationList().distinct()
    }

    override fun selectedLocationToBeRemoved(location: Location) {
        selectedItemsList.add(location)
    }

    override fun unSelectedLocationToBeRemoved(location: Location) {
        selectedItemsList.removeIf { it.id == location.id }
    }

    override fun getSelectedList(): List<Location> {
        return selectedItemsList.toList()
    }

    override fun deleteLocations() {
        weatherRepository.deleteLocations(selectedItemsList.toList())
        selectedItemsList.clear()
    }
}