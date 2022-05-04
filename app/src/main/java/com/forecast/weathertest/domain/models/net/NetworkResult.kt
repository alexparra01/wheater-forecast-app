package com.forecast.weathertest.domain.models.net

sealed class NetworkResult< out T>(
    val data: T? = null,
    val message: String? = null
) {
    data class Success<out R>(val _data: R?): NetworkResult<R>(
        data = _data,
        message = null
    )
    data class Error(val _message: String): NetworkResult<Nothing>(
        data = null,
        message= _message
    )
    data class Loading<out R>(val _data: R?, val isLoading:Boolean): NetworkResult<R>(
        data = _data,
        message = null
    )
}
