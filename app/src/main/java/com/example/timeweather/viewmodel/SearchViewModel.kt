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
import com.example.timeweather.BuildConfig
import com.example.timeweather.data.SearchRepository
import com.example.timeweather.model.location.Result
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface SearchUiState {
    data class Success(val locations : List<Result>) : SearchUiState
    data object Start : SearchUiState
    data object Error : SearchUiState
    data object Loading : SearchUiState
}

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {
    var searchUiState : SearchUiState by mutableStateOf(SearchUiState.Start)

    var userSearch by mutableStateOf("")
    var userSelected by mutableStateOf("")

    fun getLocations(
        prefix : String,
        searchKey : String
    ) {
        viewModelScope.launch {
            searchUiState = SearchUiState.Loading
            searchUiState = try {
                val results = searchRepository.getLocations(prefix = prefix, searchKey = searchKey)
                val locations = results.results ?: emptyList()
                SearchUiState.Success(locations)
            } catch (e: IOException) {
                SearchUiState.Error
            } catch (e: HttpException) {
                SearchUiState.Error
            }
        }
    }

    fun getInformation(
        lat : String,
        long : String,
        timeViewModel: TimeViewModel,
        weatherViewModel : WeatherViewModel
    ){
        viewModelScope.launch {
            timeViewModel.getTime(
                timeKey = BuildConfig.timeKey,
                lat = lat,
                long = long
            )
            weatherViewModel.getWeather(
                lat = lat,
                long = long,
                weatherKey = BuildConfig.weatherKey
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as Application)
                val searchRepository = application.container.searchRepository
                SearchViewModel(searchRepository = searchRepository)
            }
        }
    }
}
