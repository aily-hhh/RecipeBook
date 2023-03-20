package com.example.recipebook.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.recipebook.R
import com.example.recipebook.data.models.Recipe
import com.example.recipebook.data.models.Recipes
import com.example.recipebook.data.viewModels.RecipeBookViewModel
import com.example.recipebook.navigation.Screens


@Composable
fun MainScreen(navController: NavController, viewModel: RecipeBookViewModel) {
    viewModel.getAllRecipes()
    val recipesList = viewModel.allRecipes.observeAsState(listOf<Recipes>()).value
    Log.d("checkData", "Recipes: $recipesList")

    Surface(modifier = Modifier.fillMaxSize()) {
        SearchBar(onTextChange = {})
        HomeBody(listRecipes = recipesList as Recipes, navController = navController)
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier, onTextChange: (String) -> Unit) {
    OutlinedTextField(value = "",
        onValueChange = { onTextChange(it) },
        trailingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(R.string.search))
        },
        placeholder = {
            Text(stringResource(R.string.search))
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

@Composable
fun HomeBody(modifier: Modifier = Modifier, listRecipes: Recipes, navController: NavController) {
    LazyVerticalGrid(verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier.fillMaxWidth(),
        columns = GridCells.Fixed(2),
    ) {
        items(listRecipes.recipes) {
            HomeItem(recipe = it, navController = navController)
        }
    }
}

@Composable
fun HomeItem(modifier: Modifier = Modifier, recipe: Recipe, navController: NavController) {
    Card(modifier = modifier
        .padding(8.dp)
        .clickable {
                   navController.navigate(Screens.DetailScreen.route + "/${recipe.uuid}")
        },
        elevation = 6.dp
    ) {
        Column(modifier = modifier) {
            Image(painter = rememberAsyncImagePainter(recipe.images[0]),
                contentDescription = null
            )
            Icon(imageVector = Icons.Default.Home,
                contentDescription = null,
                modifier = modifier
                    .size(160.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(text = recipe.name,
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(4.dp),
                style = MaterialTheme.typography.h6,
                maxLines = 1
            )
            Text(text = recipe.description,
                modifier = modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally),
                maxLines = 1,
                style = MaterialTheme.typography.caption
            )
        }
    }
}