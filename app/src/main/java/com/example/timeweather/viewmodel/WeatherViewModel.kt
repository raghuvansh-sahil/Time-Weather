package com.example.timeweather.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.timeweather.Application
import com.example.timeweather.data.WeatherRepository
import com.example.timeweather.model.weather.WeatherData
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface WeatherUiState {
    data class Success(val weather : WeatherData) : WeatherUiState
    data object Error : WeatherUiState
    data object Loading : WeatherUiState
}


class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {
    var weatherUiState : WeatherUiState by mutableStateOf(WeatherUiState.Loading)
        private set


    fun getWeather(
        lat : String,
        long : String,
        weatherKey : String
    ) {
        viewModelScope.launch {
            weatherUiState = WeatherUiState.Loading
            weatherUiState = try {
                val weather= weatherRepository.getWeather(lat = lat, long = long, weatherKey = weatherKey)
               WeatherUiState.Success(weather)
            } catch (e: IOException) {
               WeatherUiState.Error
            } catch (e: HttpException) {
               WeatherUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as Application)
                val weatherRepository = application.container.weatherRepository
                WeatherViewModel(weatherRepository = weatherRepository)
            }
        }
    }
}