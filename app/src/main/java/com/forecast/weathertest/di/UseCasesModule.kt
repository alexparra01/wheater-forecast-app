package com.forecast.weathertest.di

import com.forecast.weathertest.domain.repository.WeatherRepository
import com.forecast.weathertest.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Singleton
    @Provides
    fun provideLocationUseCase(weatherRepository: WeatherRepository): LocationUseCase {
        return LocationUseCaseImpl(weatherRepository)
    }

    @Singleton
    @Provides
    fun provideWeatherForecastUseCase(weatherRepository: WeatherRepository): WeatherForecastUseCase {
        return WeatherForecastUseCaseImpl(weatherRepository)
    }

    @Singleton
    @Provides
    fun provideDateTransformationsUseCase(): DateTransformationUseCase {
        return DateTransformationUseCaseImpl()
    }

    @Singleton
    @Provides
    fun provideAutoCompleteUseCase(weatherRepository: WeatherRepository): AutoCompleteServiceUseCase {
        return AutoCompleteServiceUseCaseImpl(weatherRepository)
    }
}