package com.forecast.weathertest.domain.usecases

import com.forecast.weathertest.domain.repository.WeatherRepository
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import javax.inject.Inject

interface AutoCompleteServiceUseCase{
    fun getSuggestions(query: String, callback:(List<AutocompletePrediction>)-> Unit)
    fun getPlaceDetails(placeId: String, callback: (Place) -> Unit)
}
class AutoCompleteServiceUseCaseImpl @Inject constructor(
    private val weatherRepository: WeatherRepository
) : AutoCompleteServiceUseCase {
    override fun getSuggestions(query: String, callback: (List<AutocompletePrediction>) -> Unit) {
        weatherRepository.getSuggestions(query, callback)
    }

    override fun getPlaceDetails(placeId: String, callback: (Place) -> Unit) {
        weatherRepository.getPlaceDetails(placeId, callback)
    }
}