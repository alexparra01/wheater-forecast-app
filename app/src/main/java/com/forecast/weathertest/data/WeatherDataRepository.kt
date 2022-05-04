package com.forecast.weathertest.data

import com.forecast.weathertest.data.factory.WeatherDataFactory
import com.forecast.weathertest.domain.models.Forecast
import com.forecast.weathertest.domain.models.Location
import com.forecast.weathertest.domain.repository.WeatherRepository
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import retrofit2.Response
import javax.inject.Inject

class WeatherDataRepository @Inject constructor(private val weatherDataFactory: WeatherDataFactory) :
    WeatherRepository {
    override suspend fun getForecast(lat: Double, lon: Double, exclude: String, appId: String, units: String): Response<Forecast> {
        return weatherDataFactory.getWeatherForecast(lat, lon, exclude, appId, units)
    }

    override fun getLastStoredLocationList(): List<Location> {
        return weatherDataFactory.getListOfLocations()
    }

    override fun addToLocationList(location: Location) {
        return weatherDataFactory.saveListOfLocations(location)
    }

    override fun deleteLocations(deleteLocationList:List<Location>) {
        return weatherDataFactory.deleteLocations(deleteLocationList)
    }

    override fun getSuggestions(query: String, callback: (List<AutocompletePrediction>) -> Unit) {
        return weatherDataFactory.getSuggestions(query, callback)
    }

    override fun getPlaceDetails(placeId: String, callback: (Place) -> Unit) {
        return weatherDataFactory.getPlaceDetails(placeId, callback)
    }
}