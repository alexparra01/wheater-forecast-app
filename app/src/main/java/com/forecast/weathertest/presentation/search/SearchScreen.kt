package com.forecast.weathertest.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.forecast.weathertest.R
import androidx.navigation.NavHostController
import com.forecast.weathertest.domain.models.navigation.WeatherAppNavigation
import com.forecast.weathertest.presentation.components.AutoCompleteTextView
import com.forecast.weathertest.presentation.extension.navigate
import com.forecast.weathertest.presentation.util.Constants


@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    navHostController: NavHostController
) {
    SearchLayout(viewModel, navHostController)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchLayout(viewModel: SearchViewModel, navHostController: NavHostController) {
    var query by remember { mutableStateOf("") }
    var isShowingDropDown by remember { mutableStateOf(false) }
    Column (
        Modifier
            .fillMaxSize()
            .background(Color.White)
            ) {
        Spacer(modifier = Modifier.height(30.dp))
        AnimatedVisibility(visible = !isShowingDropDown ) {
            Text(
                text = stringResource(id = R.string.weather_search_title),
                textAlign = TextAlign.Center,
                color =  MaterialTheme.colors.primary,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        AutoCompleteTextView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            query = query,
            queryLabel = "Search Location",
            predictions = viewModel.listOfPlaces,
            onQueryChanged = {
                viewModel.getSuggestions(it)
                query = it
                isShowingDropDown = true
            },
            onClearClick = {
                query = ""
                viewModel.listOfPlaces.clear()
                           },
            onItemClick = {
                query = it.getFullText(null).toString()
                viewModel.listOfPlaces.clear()
                viewModel.getPlace(it.placeId)
                isShowingDropDown = false
            }
        ) {
            Text(
                text = it.getFullText(null).toString(),
                modifier = Modifier.padding(10.dp)
            )
        }
        Text(
            text = stringResource(id = R.string.recent_places),
            modifier = Modifier.clickable {
                navHostController.navigate(WeatherAppNavigation.RecentSearchListView.route)
            }.padding(10.dp),
            color =  MaterialTheme.colors.primary,
            style = TextStyle(
                textDecoration = TextDecoration.Underline
            )
        )
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(MaterialTheme.colors.primary),
                onClick = {
                    viewModel.proceedIfLocationIsNotEmpty {
                        viewModel.saveSelectedLocation()
                        navHostController.navigate(WeatherAppNavigation.ForecastView.route, Constants.LOCATION_KEY to viewModel.getLocation())
                    }
                }
            ){
                Text(text = stringResource(id = R.string.search_button_title))
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    //SearchLayout()
}