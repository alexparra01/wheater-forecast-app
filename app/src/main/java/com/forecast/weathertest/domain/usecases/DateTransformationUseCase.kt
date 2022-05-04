package com.forecast.weathertest.domain.usecases

import android.text.format.DateFormat

import java.util.*
import javax.inject.Inject

interface DateTransformationUseCase {
    fun convertTimeStampDateToDateFormat(dateTimeStamp: Long):String
    fun getDayOfWeekFromTimeStamp(dateTimeStamp: Long): String
}

class DateTransformationUseCaseImpl @Inject constructor(): DateTransformationUseCase {
    override fun convertTimeStampDateToDateFormat(dateTimeStamp: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = dateTimeStamp * 1000L
        return  DateFormat.format("MMMM dd, yyyy",calendar).toString()
    }

    override fun getDayOfWeekFromTimeStamp(dateTimeStamp: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = dateTimeStamp * 1000L
        return  DateFormat.format("EEEE",calendar).toString()
    }
}