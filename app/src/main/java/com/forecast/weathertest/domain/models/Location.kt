package com.forecast.weathertest.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Location(
    val id:String,
    val name:String,
    val lat:Double,
    val lon:Double
): Parcelable