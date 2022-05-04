package com.forecast.weathertest.presentation.forecast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.forecast.weathertest.R
import com.forecast.weathertest.data.util.Constants
import com.forecast.weathertest.domain.models.DailyForecast
import com.forecast.weathertest.domain.models.Location
import com.forecast.weathertest.domain.models.navigation.WeatherAppNavigation
import com.forecast.weathertest.presentation.components.WeatherErrorDialog
import com.skydoves.landscapist.glide.GlideImage
import kotlin.math.roundToInt

@Composable
fun ForecastScreen(
    location: Location?,
    viewModel: ForecastViewModel,
    navHostController: NavHostController
) {
    var isShowingTitle by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        viewModel.setLocation(location)
        viewModel.retrieveWeather()
        isShowingTitle = true
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {
        AnimatedVisibility(visible = isShowingTitle) {
            Text(
                modifier= Modifier.padding(20.dp),
                text = stringResource(id = R.string.the_forecast_in) + " ${viewModel.getLocation().name} " + stringResource(id = R.string.over_the_next),
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        AnimatedVisibility(visible = viewModel.isLoading()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        AnimatedVisibility(visible = !viewModel.isLoading()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.background(MaterialTheme.colors.primary)
            ){
                items(viewModel.getForecast().daily) { forecast ->
                    ForecastItem(forecast = forecast, viewModel = viewModel)
                }
            }
        }

        WeatherErrorDialog(value = viewModel.hasError()) {
            viewModel.setHasError(false)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id =R.string.return_to_search),
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(10.dp, 20.dp, 10.dp, 20.dp)
                .clickable { navHostController.navigate(WeatherAppNavigation.SearchView.route) },
            style = TextStyle(textDecoration = TextDecoration.Underline)
        )
    }
}

@Composable
fun ForecastItem(forecast: DailyForecast, viewModel: ForecastViewModel){
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.padding(7.dp),
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier
                .height(250.dp)
                .width(280.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = viewModel.getDayOfWeek(forecast.dt),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color.Black
            )
            Text(
                text = viewModel.convertTimeStampToDateFormat(forecast.dt)
            )
            Row (
                modifier = Modifier.padding(10.dp)
            ) {
                GlideImage(
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp),
                    imageModel = "${Constants.ICON_URL}${forecast.weather.first().icon}@2x.png",
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "${forecast.temp.day.roundToInt()}°C",
                    fontSize = 34.sp
                )
            }
            Text(
                text = forecast.weather.first().description,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${stringResource(id = R.string.the_high_will_be)} ${forecast.temp.max.roundToInt()}°C, ${stringResource(id = R.string.the_low_will_be)} ${forecast.temp.min.roundToInt()}°C."
            )

        }
    }

}