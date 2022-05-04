package com.forecast.weathertest.presentation.extension

import android.os.Parcelable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

inline fun <reified T : Parcelable> NavBackStackEntry.requiredArg(key: String): T? {
    return requireNotNull(arguments) { return null }.run {
        requireNotNull(getParcelable(key)) { "argument for $key is null" }
    }
}

fun NavController.navigate(route: String, vararg args: Pair<String, Parcelable>) {
    navigate(route)

    requireNotNull(currentBackStackEntry?.arguments).apply {
        args.forEach { (key: String, arg: Parcelable) ->
            putParcelable(key, arg)
        }
    }
}