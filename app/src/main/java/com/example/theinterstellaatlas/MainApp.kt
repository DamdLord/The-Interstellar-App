package com.example.theinterstellaatlas

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.theinterstellaatlas.screens.DetailScreen
import com.example.theinterstellaatlas.screens.HomeScreen


enum class Screens(val title :String){
    HOME("Home"),
    DETAILS("Details"),
}
@Composable
fun MainApp(
    modifier: Modifier = Modifier
){
    val navController  = rememberNavController()
    val appViewModel: AppViewModel = viewModel(factory = AppViewModel.factory)
    val uiState = appViewModel.uiState.collectAsState().value
    val weatherResponse = appViewModel.weatherData.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = Screens.HOME.name
    ){
        composable(route = Screens.HOME.name){
            HomeScreen(
                appViewModel = appViewModel,
                uiState = uiState,
                navController = navController
            )
        }
        composable(route = Screens.DETAILS.name){
            DetailScreen(
                navController = navController,
                appViewModel = appViewModel,
                weatherResponse =weatherResponse ,
                country = uiState.countryToShowDetails
            )
        }

    }
}