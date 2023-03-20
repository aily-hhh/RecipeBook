package com.example.recipebook.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.recipebook.data.viewModels.RecipeBookViewModel

@Composable
fun DetailScreen(navController: NavController, viewModel: RecipeBookViewModel, itemId: String) {
    Text(text = "Details screen, item id: $itemId")
}