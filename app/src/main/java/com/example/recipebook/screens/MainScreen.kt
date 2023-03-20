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
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
    val recipesList = viewModel.allRecipes.observeAsState(Recipes(listOf())).value
    Log.d("checkData", "Recipes: $recipesList")
    viewModel.getAllRecipes()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
    ) {
        SearchBar(onTextChange = {})
        HomeBody(listRecipes = recipesList, navController = navController)
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier, onTextChange: (String) -> Unit) {
    val textInput = rememberSaveable {
        mutableStateOf("")
    }
    OutlinedTextField(value = textInput.value,
        onValueChange = {
            onTextChange(it)
            textInput.value = it
        },
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
        contentPadding = PaddingValues(8.dp),
        modifier = modifier.fillMaxWidth(),
        columns = GridCells.Fixed(1)
    ) {
        if (listRecipes.recipes.isNotEmpty()) {
            items(listRecipes.recipes) {
                HomeItem(recipe = it, navController = navController)
            }
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
        elevation = 6.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = modifier) {
            if (!recipe.images.isNullOrEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(recipe.images[0]),
                    contentDescription = null,
                    modifier = modifier.sizeIn(150.dp)
                )
            }
            if (!recipe.name.isNullOrEmpty()) {
                Text(
                    text = recipe.name,
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp),
                    style = MaterialTheme.typography.h6
                )
            }
            if (!recipe.description.isNullOrEmpty()) {
                Text(
                    text = recipe.description,
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp),
                    maxLines = 3,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}