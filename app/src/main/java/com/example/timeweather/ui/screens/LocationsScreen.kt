package com.example.timeweather.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.timeweather.R
import com.example.timeweather.TimeWeatherScreen
import com.example.timeweather.model.location.Result
import com.example.timeweather.viewmodel.SearchUiState
import com.example.timeweather.viewmodel.SearchViewModel
import com.example.timeweather.viewmodel.TimeViewModel
import com.example.timeweather.viewmodel.WeatherViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun LocationsScreen(
    searchKey : String,
    searchViewModel: SearchViewModel,
    timeViewModel : TimeViewModel,
    weatherViewModel: WeatherViewModel,
    navController : NavController,
    modifier : Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var userInput by remember { mutableStateOf(searchViewModel.userSearch) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .safeDrawingPadding()
    ) {
        SearchBar(
            userSearch = userInput,
            onUserSearchChanged = { newSearch ->
                userInput = newSearch
                searchViewModel.userSearch = newSearch
            },
            onSearch = {
                searchViewModel.getLocations(searchViewModel.userSearch, searchKey)
                keyboardController?.hide()
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        when (searchViewModel.searchUiState) {
            is SearchUiState.Start -> { }
            is SearchUiState.Loading -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            is SearchUiState.Success -> {
                LocationList(
                    locations = (searchViewModel.searchUiState as SearchUiState.Success).locations,
                    searchViewModel = searchViewModel,
                    timeViewModel = timeViewModel,
                    weatherViewModel = weatherViewModel,
                    navController = navController
                )
            }
            is SearchUiState.Error -> {
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
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    userSearch : String,
    onUserSearchChanged : (String) -> Unit,
    onSearch : () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val debounceJob = remember { mutableStateOf<Job?>(null) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = userSearch,
            onValueChange = {
                onUserSearchChanged(it)

                debounceJob.value?.cancel()
                debounceJob.value = CoroutineScope(Dispatchers.Main).launch {
                    delay(300)
                    onSearch()
                }
            },
            label = { Text("Search Location") },
            shape = RoundedCornerShape(35.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.size(4.dp))
        Box {
            Button(
                onClick = onSearch,
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF9DD9F3)),
                modifier = Modifier.padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search),
                    tint = Color(0xFF015C92)
                )
            }
        }
    }
}

@Composable
private fun LocationList(
    locations : List<Result>,
    searchViewModel: SearchViewModel,
    timeViewModel : TimeViewModel,
    weatherViewModel: WeatherViewModel,
    navController : NavController,
    modifier : Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    if (locations.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.no_results_found),
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF015C92)
            )
        }
    }
    else {
        LazyColumn(
            modifier = modifier.padding(horizontal = 4.dp),
            contentPadding = contentPadding
        ) {
            items(items = locations) { location ->
                LocationButton(
                    name = location,
                    searchViewModel = searchViewModel,
                    timeViewModel = timeViewModel,
                    weatherViewModel = weatherViewModel,
                    navController = navController
                )
            }
        }
    }
}

@Composable
private fun LocationButton(
    name : Result,
    searchViewModel: SearchViewModel,
    timeViewModel : TimeViewModel,
    weatherViewModel: WeatherViewModel,
    navController : NavController,
    modifier : Modifier = Modifier
) {
    Button(
        onClick = {
            val lat = name.geometry.lat
            val long = name.geometry.lng
            searchViewModel.userSelected = name.formatted
            searchViewModel.userSearch = ""

            searchViewModel.getInformation(
                lat = lat,
                long = long,
                timeViewModel = timeViewModel,
                weatherViewModel = weatherViewModel
            )

            navController.navigate(route = TimeWeatherScreen.Display.name)
        },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(Color(0xFF9DD9F3)),
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(4.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1.8f)
            ) {
                Text(
                    text = name.formatted,
                    color = Color(0xFF015C92),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(0.2f)
            ) {
                Text(
                    text = name.annotations.flag
                )
            }
        }
    }
}