package com.forecast.weathertest.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun WeatherErrorDialog(value: Boolean, callback:() -> Unit) {
    AnimatedVisibility(visible = value) {
        AlertDialog(
            title = {
                Text(
                    text = "Error",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            text = {
                Text(
                    text = "Error getting information",
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )
            },
            onDismissRequest = { },
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.55f),
            confirmButton = {},
            dismissButton = {
                OutlinedButton(onClick = { callback.invoke() }) {
                    Text(
                        text = "Dismiss",
                        style = TextStyle(
                            fontSize = 18.sp
                        ),
                        color = Color.Black
                    )
                }
            }
        )
    }
}