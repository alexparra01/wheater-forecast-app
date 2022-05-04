package com.forecast.weathertest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.forecast.weathertest.domain.models.navigation.WeatherAppNavigation
import com.forecast.weathertest.presentation.extension.requiredArg
import com.forecast.weathertest.presentation.forecast.ForecastScreen
import com.forecast.weathertest.presentation.recent.RecentPlacesScreen
import com.forecast.weathertest.presentation.search.SearchScreen
import com.forecast.weathertest.presentation.util.Constants

@Composable
fun AppNavigation(initialDestination: String) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination =  initialDestination
    ) {

        composable(route = WeatherAppNavigation.SearchView.route) { navBackStackEntry ->
            SearchScreen(
                viewModel = hiltViewModel(navBackStackEntry),
                navHostController = navController
            )
        }

        composable(route = WeatherAppNavigation.ForecastView.route) { navBackStackEntry ->
            ForecastScreen(
                location = navBackStackEntry.requiredArg(Constants.LOCATION_KEY),
                viewModel = hiltViewModel(navBackStackEntry),
                navHostController = navController
            )
        }
        composable(route = WeatherAppNavigation.RecentSearchListView.route){ navBackStackEntry ->
            RecentPlacesScreen(
                viewModel = hiltViewModel(navBackStackEntry),
                navHostController = navController
            )
        }
    }
}