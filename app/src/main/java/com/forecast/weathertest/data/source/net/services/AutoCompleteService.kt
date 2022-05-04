package com.forecast.weathertest.data.source.net.services

import android.util.Log
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import javax.inject.Inject


const val TAG = "AutocompleteService"

interface AutoCompleteService {
    fun getSuggestions(query: String, callback:(List<AutocompletePrediction>)-> Unit)
    fun getPlaceDetails(placeId: String, callback: (Place) -> Unit)
}

class AutoCompleteServiceImpl @Inject constructor(
    private val placesClient: PlacesClient
): AutoCompleteService {
    override fun getSuggestions(query: String, callback: (List<AutocompletePrediction>) -> Unit) {
        val token = AutocompleteSessionToken.newInstance()

        val request = FindAutocompletePredictionsRequest.builder()
            .setCountry("AU")
            .setTypeFilter(TypeFilter.CITIES)
            .setTypeFilter(TypeFilter.ADDRESS)
            .setTypeFilter(TypeFilter.GEOCODE)
            .setSessionToken(token)
            .setQuery(query)
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                callback.invoke(response.autocompletePredictions)
            }
            .addOnFailureListener {
                Log.e(TAG, "Place not found: ${it.message}")
            }
    }

    override fun getPlaceDetails(placeId: String, callback: (Place) -> Unit) {
        val placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
        val request = FetchPlaceRequest.newInstance(placeId, placeFields)

        placesClient.fetchPlace(request)
            .addOnSuccessListener {
                callback.invoke(it.place)
            }
            .addOnFailureListener {
                Log.e(TAG, "Place not found: ${it.message}")
            }
    }
}