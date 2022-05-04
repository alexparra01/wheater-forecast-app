package com.forecast.weathertest.presentation.recent


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.forecast.weathertest.R
import com.forecast.weathertest.domain.models.Location
import com.forecast.weathertest.domain.models.navigation.WeatherAppNavigation
import com.forecast.weathertest.presentation.extension.navigate
import com.forecast.weathertest.presentation.util.Constants

@Composable
fun RecentPlacesScreen(
    viewModel: RecentPlacesViewModel,
    navHostController: NavHostController
){
    var isShowingDeleteButton by remember { mutableStateOf(false) }
    var places by remember { mutableStateOf(viewModel.retrieveSavedLocation()) }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.recent_location_title),
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp, 20.dp, 10.dp, 20.dp)
            )
            AnimatedVisibility(visible = isShowingDeleteButton) {
                Icon(
                    imageVector = Icons.Filled.Delete, contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(30.dp)
                        .clickable {
                            viewModel.deleteLocations()
                            places = viewModel.retrieveSavedLocation()
                            isShowingDeleteButton = false
                        }
                )
            }
        }

        Text(
            text = stringResource(id =R.string.return_to_search),
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(10.dp, 20.dp, 10.dp, 20.dp)
                .clickable { navHostController.navigate(WeatherAppNavigation.SearchView.route) }
        )

        LazyColumn{
            items(
                items = places,
                key = { location -> location.id }
            ){ location ->
                RecentPlacesItem(
                    viewModel = viewModel,
                    location = location,
                    navHostController = navHostController
                ){
                    isShowingDeleteButton = viewModel.isThereAnySelectedItem()
                }
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecentPlacesItem(
    viewModel: RecentPlacesViewModel,
    location: Location,
    navHostController: NavHostController,
    callback:() -> Unit){
    var isSelected by remember { mutableStateOf(false)}
    var backgroundColor by remember { mutableStateOf(Color.White) }

    Card(
        shape = RoundedCornerShape(2.dp),
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize()
            .combinedClickable(
                onClick = {
                    navHostController
                        .navigate(
                            WeatherAppNavigation.ForecastView.route,
                            Constants.LOCATION_KEY to location
                        )
                },
                onLongClick = {
                    isSelected = !isSelected
                    backgroundColor = if (isSelected)
                        Color.Gray
                    else
                        Color.White

                    if (isSelected)
                        viewModel.selectedLocationToBeRemoved(location)
                    else
                        viewModel.unSelectedLocationToBeRemoved(location)

                    callback.invoke()
                }
            ),
        backgroundColor = backgroundColor
    ) {
        Column {
            Text(
                text = "Location : ${location.name}",
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(5.dp)
            )
            Row {
                Text(
                    text = "Latitude: ${location.lat}",
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = "Longitude: ${location.lon}",
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}