package com.forecast.weathertest.domain.models.navigation

const val SEARCH_VIEW = "search_view"
const val RECENT_SEARCH_LIST_VIEW = "recent_search_list_view"
const val FORECAST_VIEW = "forecast_view"

sealed class WeatherAppNavigation(val route: String){
    object SearchView : WeatherAppNavigation(SEARCH_VIEW)
    object RecentSearchListView : WeatherAppNavigation(RECENT_SEARCH_LIST_VIEW)
    object ForecastView : WeatherAppNavigation(FORECAST_VIEW)
}
