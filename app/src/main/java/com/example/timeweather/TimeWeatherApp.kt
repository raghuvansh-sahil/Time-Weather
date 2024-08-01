package com.example.timeweather

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.timeweather.ui.screens.LocationsScreen
import com.example.timeweather.ui.screens.WeatherScreen
import com.example.timeweather.viewmodel.SearchViewModel
import com.example.timeweather.viewmodel.TimeViewModel
import com.example.timeweather.viewmodel.WeatherViewModel

enum class TimeWeatherScreen {
    Display,
    Select
}

@Composable
fun TimeWeatherApp(
    searchKey : String
) {
    val searchViewModel : SearchViewModel = viewModel(factory = SearchViewModel.Factory)
    val timeViewModel : TimeViewModel = viewModel(factory = TimeViewModel.Factory)
    val weatherViewModel : WeatherViewModel = viewModel(factory = WeatherViewModel.Factory)

    val navController : NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TimeWeatherScreen.Select.name
    ) {
        composable(route = TimeWeatherScreen.Select.name) {
            LocationsScreen(
                searchKey = searchKey,
                searchViewModel = searchViewModel,
                timeViewModel = timeViewModel,
                weatherViewModel = weatherViewModel,
                navController = navController
            )
        }
        composable(route = TimeWeatherScreen.Display.name) {
            WeatherScreen(
                searchViewModel = searchViewModel,
                timeViewModel = timeViewModel,
                weatherViewModel = weatherViewModel,
                navController = navController
            )
        }
    }
}