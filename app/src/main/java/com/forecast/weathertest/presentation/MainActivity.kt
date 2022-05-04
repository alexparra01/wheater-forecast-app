package com.forecast.weathertest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.forecast.weathertest.BuildConfig
import com.forecast.weathertest.domain.models.navigation.WeatherAppNavigation
import com.forecast.weathertest.presentation.navigation.AppNavigation
import com.forecast.weathertest.presentation.theme.WeatherTestTheme
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.AndroidEntryPoint
import com.microsoft.appcenter.crashes.Crashes

import com.microsoft.appcenter.analytics.Analytics

import com.microsoft.appcenter.AppCenter




@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var initialScreen :String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!Places.isInitialized()){
            Places.initialize(this, BuildConfig.apiKey)
        }
        AppCenter.start(
            application, BuildConfig.appCenterKey,
            Analytics::class.java, Crashes::class.java
        )
        viewModel.handleStartNavigation({
            initialScreen = WeatherAppNavigation.SearchView.route
        }){
            initialScreen = WeatherAppNavigation.ForecastView.route
        }
        setContent {
            WeatherTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    AppNavigation(initialScreen)
                }
            }
        }
    }
}