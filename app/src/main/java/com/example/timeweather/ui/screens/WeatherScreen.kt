package com.example.timeweather.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.timeweather.R
import com.example.timeweather.TimeWeatherScreen
import com.example.timeweather.data.WeatherCodes
import com.example.timeweather.viewmodel.SearchViewModel
import com.example.timeweather.viewmodel.TimeUiState
import com.example.timeweather.viewmodel.TimeViewModel
import com.example.timeweather.viewmodel.WeatherUiState
import com.example.timeweather.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    searchViewModel: SearchViewModel,
    timeViewModel : TimeViewModel,
    weatherViewModel: WeatherViewModel,
    navController: NavController
) {
    val timeUiState = timeViewModel.timeUiState
    val weatherUiState = weatherViewModel.weatherUiState

    when {
        timeUiState is TimeUiState.Loading || weatherUiState is WeatherUiState.Loading -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
        timeUiState is TimeUiState.Error || weatherUiState is WeatherUiState.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_connection_error),
                    contentDescription = ""
                )
                Text(text = stringResource(R.string.loading_failed),
                    modifier = Modifier.padding(16.dp)
                )
                Button(onClick = {}) {
                    Text(stringResource(R.string.retry))
                }
            }
        }
        timeUiState is TimeUiState.Success && weatherUiState is WeatherUiState.Success -> {
            val time = timeUiState.time
            val weather = weatherUiState.weather

            Image(
                painter = painterResource(WeatherCodes.getColor(weather.weather[0].id)),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
                    .safeDrawingPadding()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .width(350.dp)
                        .padding(8.dp)
                ) {
                    Box {
                        Button(
                            onClick = { navController.navigate(route = TimeWeatherScreen.Select.name) },
                            shape = RoundedCornerShape(100.dp),
                            colors = ButtonDefaults.buttonColors(Color(0x664D4D4D)),
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(R.string.search),
                                tint = Color.White
                            )
                        }
                    }
                }
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(Color(0x664D4D4D)),
                    modifier = Modifier
                        .width(350.dp)
                        .height(50.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = time.formatted,
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.size(4.dp))
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(Color(0x664D4D4D)),
                    modifier = Modifier
                        .width(350.dp)
                        .height(300.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .weight(0.8f)
                        ) {
                            Text(
                                text = searchViewModel.userSelected,
                                color = Color.White,
                                style = MaterialTheme.typography.bodyLarge,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.fillMaxWidth()

                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(4.dp)
                                .weight(1.2f)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .weight(1.2f)
                            ) {
                                Text(
                                    text = "${weather.main.temp}\u00B0C",
                                    color = Color.White,
                                    style = MaterialTheme.typography.displayMedium,
                                )
                                Text(
                                    text = "Feels like ${weather.main.feels_like}°C",
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = "${weather.main.temp_min}\u00B0C / ${weather.main.temp_max}°C",
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.End,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(8.dp)
                                    .weight(0.8f)
                            ) {
                                Box(
                                    modifier = Modifier.size(90.dp)
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(context = LocalContext.current)
                                            .data("https://openweathermap.org/img/wn/${weather.weather[0].icon}@2x.png")
                                            .crossfade(true)
                                            .build(),
                                        error = painterResource(R.drawable.ic_broken_image),
                                        placeholder = painterResource(R.drawable.loading_img),
                                        contentDescription = "icon",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.size(4.dp))
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(Color(0x664D4D4D)),
                    modifier = Modifier
                        .width(350.dp)
                        .height(50.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = weather.weather[0].description,
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.size(20.dp))
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(Color(0x664D4D4D)),
                    modifier = Modifier
                        .width(350.dp)
                        .height(50.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.pressure),
                            color = Color.White,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "${weather.main.pressure} mb",
                            color = Color.White,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
                Spacer(modifier = Modifier.size(4.dp))
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(Color(0x664D4D4D)),
                    modifier = Modifier
                        .width(350.dp)
                        .height(50.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.humidity),
                            color = Color.White,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "${weather.main.humidity}%",
                            color = Color.White,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }
        }
    }
}