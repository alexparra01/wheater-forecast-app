package com.forecast.weathertest.data.factory

import com.forecast.weathertest.data.source.local.LocalDataSource
import com.forecast.weathertest.data.source.net.NetDataSource
import com.forecast.weathertest.data.source.net.services.AutoCompleteService
import com.forecast.weathertest.domain.models.Location
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import javax.inject.Inject

class WeatherDataFactory @Inject constructor(
    private val netDataSource: NetDataSource,
    private val localDataSource: LocalDataSource,
    private val autoCompleteService: AutoCompleteService
    ) {
    suspend fun getWeatherForecast(lat: Double, lon:Double, exclude: String, appId: String, units: String) = netDataSource.forecast(lat, lon, exclude, appId, units)
    fun saveListOfLocations(location: Location) = localDataSource.addToLocationList(location)
    fun getListOfLocations(): List<Location> = localDataSource.getLastLocationList()
    fun deleteLocations(deleteLocationList: List<Location>) = localDataSource.deleteLocations(deleteLocationList)
    fun getSuggestions(query: String, callback:(List<AutocompletePrediction>)-> Unit) = autoCompleteService.getSuggestions(query, callback)
    fun getPlaceDetails(placeId: String, callback: (Place) -> Unit) = autoCompleteService.getPlaceDetails(placeId, callback)
}