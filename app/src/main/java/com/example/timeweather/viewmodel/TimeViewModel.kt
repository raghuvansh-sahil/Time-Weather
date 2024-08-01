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
import com.example.timeweather.data.TimeRepository
import com.example.timeweather.model.Time
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed interface TimeUiState {
    data class Success(val time : Time) : TimeUiState
    data object Error : TimeUiState
    data object Loading : TimeUiState
}

class TimeViewModel(private val timeRepository: TimeRepository) : ViewModel() {
    var timeUiState : TimeUiState by mutableStateOf(TimeUiState.Loading)
        private set

    fun getTime(
        timeKey : String,
        lat : String,
        long : String
    ) {
        viewModelScope.launch {
            timeUiState = TimeUiState.Loading
            timeUiState = try {
                val time = timeRepository.getTime(timeKey = timeKey, lat = lat, long = long)
                TimeUiState.Success(time)
            } catch (e: IOException) {
                TimeUiState.Error
            } catch (e: HttpException) {
                TimeUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as Application)
                val timeRepository = application.container.timeRepository
                TimeViewModel(timeRepository = timeRepository)
            }
        }
    }
}