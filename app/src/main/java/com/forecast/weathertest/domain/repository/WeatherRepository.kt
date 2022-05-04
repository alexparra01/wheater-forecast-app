package com.forecast.weathertest.domain.repository

import com.forecast.weathertest.domain.models.Forecast
import com.forecast.weathertest.domain.models.Location
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import retrofit2.Response

interface WeatherRepository {
    suspend fun getForecast(lat: Double, lon: Double, exclude: String, appId: String, units: String): Response<Forecast>
    fun getLastStoredLocationList():List<Location>
    fun addToLocationList(location: Location)
    fun deleteLocations(deleteLocationList:List<Location>)
    fun getSuggestions(query: String, callback:(List<AutocompletePrediction>)-> Unit)
    fun getPlaceDetails(placeId: String, callback: (Place) -> Unit)
}