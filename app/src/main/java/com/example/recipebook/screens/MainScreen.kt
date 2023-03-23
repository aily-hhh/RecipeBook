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
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.recipebook.R
import com.example.recipebook.data.models.Recipe
import com.example.recipebook.data.models.Recipes
import com.example.recipebook.data.viewModels.RecipeBookViewModel
import com.example.recipebook.navigation.Screens
import com.example.recipebook.ui.theme.RecipeBookTheme


@Composable
fun MainScreen(navController: NavController, viewModel: RecipeBookViewModel) {
    val recipesList = viewModel.allRecipes.observeAsState(Recipes(listOf())).value
    Log.d("checkData", "Recipes: $recipesList")
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
    ) {
        val expanded = rememberSaveable { mutableStateOf(false) }
        val selectedOption = rememberSaveable { mutableStateOf("By default") }

        SearchBar(
            onTextChange = {
                if (it == "") {
                    updateRecipes(selectedOption.value, viewModel)
                } else {
                    viewModel.performQuery(it)
                } },
            selectedOption = selectedOption.value,
            viewModel = viewModel
        )
        Box {
            TextButton(
                onClick = {
                    expanded.value = true
                }
            ) {
                Row {
                    Text(text = selectedOption.value)
                    if (expanded.value) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "DropDown menu",
                            Modifier.rotate(180f)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "DropDown menu"
                        )
                    }
                }
            }
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                DropdownMenuItem(
                    onClick = {
                        viewModel.getAllRecipes()
                        selectedOption.value = "By default"
                        expanded.value = false
                    }
                ) {
                    Row {
                        Text(text = stringResource(R.string.by_default))
                    }
                }
                DropdownMenuItem(
                    onClick = {
                        viewModel.sortDateAsc()
                        selectedOption.value = "By date (asc)"
                        expanded.value = false
                    }
                ) {
                    Row {
                        Text(text = stringResource(R.string.by_date))
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Sort by date up",
                            Modifier.rotate(90f)
                        )
                    }
                }
                DropdownMenuItem(
                    onClick = {
                        viewModel.sortDateDesc()
                        selectedOption.value = "By date (desc)"
                        expanded.value = false
                    }
                ) {
                    Row {
                        Text(text = stringResource(R.string.by_date))
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Sort by date down",
                            Modifier.rotate(-90f)
                        )
                    }
                }
                DropdownMenuItem(
                    onClick = {
                        viewModel.sortNameAsc()
                        selectedOption.value = "By name (asc)"
                        expanded.value = false
                    }
                ) {
                    Row {
                        Text(text = stringResource(R.string.by_name))
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Sort by name up",
                            Modifier.rotate(90f)
                        )
                    }
                }
                DropdownMenuItem(
                    onClick = {
                        viewModel.sortNameDesc()
                        selectedOption.value = "By name (desc)"
                        expanded.value = false
                    }
                ) {
                    Row {
                        Text(text = stringResource(R.string.by_name))
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Sort by name down",
                            Modifier.rotate(-90f)
                        )
                    }
                }
            }
        }
        HomeBody(listRecipes = recipesList, navController = navController)
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit,
    selectedOption: String,
    viewModel: RecipeBookViewModel
) {
    val textInput = rememberSaveable {
        mutableStateOf("")
    }
    OutlinedTextField(value = textInput.value,
        onValueChange = {
            onTextChange(it)
            textInput.value = it
        },
        trailingIcon = {
            if (textInput.value == "") {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.search),
                    modifier = modifier.clickable {
                        textInput.value = ""
                        updateRecipes(selectedOption, viewModel)
                    }
                )
            }
        },
        placeholder = {
            Text(stringResource(R.string.search))
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        singleLine = true
    )
}

@Composable
fun HomeBody(modifier: Modifier = Modifier, listRecipes: Recipes, navController: NavController) {
    LazyVerticalGrid(
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
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = modifier) {
            if (!recipe.images.isNullOrEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(recipe.images[0]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(196.dp)
                        .fillMaxWidth()
                )
            }
            if (!recipe.name.isNullOrEmpty()) {
                Text(
                    text = recipe.name,
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    style = MaterialTheme.typography.h6
                )
            }
            if (!recipe.description.isNullOrEmpty()) {
                Text(
                    text = recipe.description,
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 4.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
                    maxLines = 2,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}

private fun updateRecipes(value: String, viewModel: RecipeBookViewModel) {
    if (value == "By default") {
        viewModel.getAllRecipes()
    }
    if (value == "By date (asc)") {
        viewModel.getAllRecipesDateAsc()
    }
    if (value == "By date (desc)") {
        viewModel.getAllRecipesDateDesc()
    }
    if (value == "By name (asc)") {
        viewModel.getAllRecipesNameAsc()
    }
    if (value == "By name (desc)") {
        viewModel.getAllRecipesNameDesc()
    }
}