package com.forecast.weathertest.data.source.local

import android.content.SharedPreferences
import com.forecast.weathertest.data.util.Constants
import com.forecast.weathertest.domain.models.Location
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

interface LocalDataSource {
    fun getLastLocationList():List<Location>
    fun addToLocationList(location: Location)
    fun deleteLocations(deleteLocationList:List<Location>)
}

class LocalDataSourceImpl @Inject constructor (private val sharedPrefs: SharedPreferences, private val moshi: Moshi) : LocalDataSource{

    private var locationList = mutableListOf<Location>()
    private val type: ParameterizedType = Types.newParameterizedType(List::class.java, Location::class.java)


    override fun getLastLocationList(): List<Location> {
        val jsonList = sharedPrefs.getString(Constants.SHARED_PREFS,"")
        if (!jsonList.isNullOrEmpty()) {
            locationList = moshi.adapter<List<Location>>(type).fromJson(jsonList).orEmpty().toMutableList()
        }
        return locationList.toList()
    }

    override fun addToLocationList(location: Location) {
        locationList.add(location)
        saveInSharedPref()
    }

    override fun deleteLocations(deleteLocationList:List<Location>) {
        deleteLocationList.forEach { location ->
            locationList.removeIf { it.id == location.id  }
        }
        saveInSharedPref()
    }

    private fun saveInSharedPref() {
        val jsonList = moshi.adapter<List<Location>>(type).toJson(locationList.toList())
        sharedPrefs.edit().putString(Constants.SHARED_PREFS, jsonList).apply()
    }
}