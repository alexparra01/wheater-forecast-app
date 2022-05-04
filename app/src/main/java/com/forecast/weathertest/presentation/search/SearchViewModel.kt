package com.forecast.weathertest.presentation.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.forecast.weathertest.domain.models.Location
import com.forecast.weathertest.domain.usecases.AutoCompleteServiceUseCase
import com.forecast.weathertest.domain.usecases.AutoCompleteServiceUseCaseImpl
import com.forecast.weathertest.domain.usecases.LocationUseCase
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

const val TAG = "SearchViewModel"

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val autoCompleteServiceUseCase: AutoCompleteServiceUseCase,
    private val locationUseCase: LocationUseCase
) : ViewModel() {
    val listOfPlaces by mutableStateOf(mutableListOf<AutocompletePrediction>())
    private lateinit var location: Location

    fun getSuggestions(query: String){
        if (query.isEmpty()){
            listOfPlaces.clear()
        }
        autoCompleteServiceUseCase.getSuggestions(query) {
            listOfPlaces.clear()
            listOfPlaces.addAll(it)
        }
    }

    fun getPlace(placeId: String){
        autoCompleteServiceUseCase.getPlaceDetails(placeId) {
            location = Location(
                UUID.randomUUID().toString(),
                it.name,
                it.latLng.latitude,
                it.latLng.longitude
            )
            Log.i(TAG, "Place: ${it.latLng?.latitude}, ${it.latLng?.longitude}")
        }
    }

    fun getLocation() = location

    fun saveSelectedLocation(){ locationUseCase.saveRecentLocation(location) }

    fun proceedIfLocationIsNotEmpty(callback:() -> Unit) {
        if(::location.isInitialized){
            callback.invoke()
        }
    }
}