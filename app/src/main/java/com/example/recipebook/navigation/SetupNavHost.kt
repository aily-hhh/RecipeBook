package com.example.recipebook.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.recipebook.data.viewModels.RecipeBookViewModel
import com.example.recipebook.screens.DetailScreen
import com.example.recipebook.screens.ImageScreen
import com.example.recipebook.screens.MainScreen
import com.example.recipebook.utils.Constants

sealed class Screens(val route: String) {
    object MainScreen: Screens(route = Constants.Screens.MAIN_SCREEN)
    object DetailScreen: Screens(route = Constants.Screens.DETAILS_SCREEN)
    object ImageScreen: Screens(route = Constants.Screens.IMAGE_SCREEN)
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SetupNavHost(navController: NavHostController, viewModel: RecipeBookViewModel) {
    NavHost(navController = navController,
        startDestination = Screens.MainScreen.route
    ) {
        composable(route = Screens.MainScreen.route) {
            MainScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = Screens.DetailScreen.route + "/{id}") { backStackEntry ->
            DetailScreen(
                viewModel = viewModel,
                navController = navController,
                itemId = backStackEntry.arguments?.getString("id") ?: ""
            )
        }
        composable(
            route = Screens.ImageScreen.route + "/{item}",
            arguments = listOf(
                navArgument("item") {type = NavType.StringType}
            )
        ) {backStackEntry ->
            backStackEntry.arguments?.getString("item")?.let {
                ImageScreen(itemUrl = it)
            }
        }
    }
}