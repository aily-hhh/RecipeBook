package com.example.recipebook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recipebook.screens.DetailScreen
import com.example.recipebook.screens.MainScreen
import com.example.recipebook.utils.Constants

sealed class Screens(val route: String) {
    object MainScreen: Screens(route = Constants.Screens.MAIN_SCREEN)
    object DetailScreen: Screens(route = Constants.Screens.DETAILS_SCREEN)
}

@Composable
fun SetupNavHost(navController: NavHostController) {
    NavHost(navController = navController,
        startDestination = Screens.MainScreen.route
    ) {
        composable(route = Screens.MainScreen.route) {
            MainScreen()
        }
        composable(route = Screens.DetailScreen.route) {
            DetailScreen()
        }
    }
}