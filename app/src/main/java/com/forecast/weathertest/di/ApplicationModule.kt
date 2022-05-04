package com.forecast.weathertest.di

import android.content.Context
import android.content.SharedPreferences
import com.forecast.weathertest.data.WeatherDataRepository
import com.forecast.weathertest.data.factory.WeatherDataFactory
import com.forecast.weathertest.data.source.local.LocalDataSource
import com.forecast.weathertest.data.source.local.LocalDataSourceImpl
import com.forecast.weathertest.data.source.net.NetDataSource
import com.forecast.weathertest.data.source.net.NetDataSourceImpl
import com.forecast.weathertest.data.source.net.WeatherRestApi
import com.forecast.weathertest.data.source.net.services.AutoCompleteService
import com.forecast.weathertest.data.source.net.services.AutoCompleteServiceImpl
import com.forecast.weathertest.data.util.Constants
import com.forecast.weathertest.domain.repository.WeatherRepository
import com.google.android.libraries.places.api.net.PlacesClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideDataFactory(netDataSource: NetDataSource, localDataSource: LocalDataSource, autoCompleteService: AutoCompleteService): WeatherDataFactory {
        return WeatherDataFactory(netDataSource, localDataSource, autoCompleteService)
    }

    @Singleton
    @Provides
    fun provideNetDataSource(weatherRestApi: WeatherRestApi): NetDataSource{
        return NetDataSourceImpl(weatherRestApi)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(sharedPrefs: SharedPreferences, moshi: Moshi): LocalDataSource{
        return LocalDataSourceImpl(sharedPrefs, moshi)
    }

    @Singleton
    @Provides
    fun provideWeatherDataRepository(weatherDataFactory: WeatherDataFactory): WeatherRepository {
        return WeatherDataRepository(weatherDataFactory)
    }

    @Singleton
    @Provides
    fun provideAutoCompleteService(placesClient: PlacesClient): AutoCompleteService {
        return AutoCompleteServiceImpl(placesClient)
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideMoshiClient(): Moshi{
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}