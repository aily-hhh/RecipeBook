package com.example.recipebook.screens

import android.content.res.Resources.Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.recipebook.data.models.Recipe
import com.example.recipebook.data.models.Recipes
import com.example.recipebook.data.viewModels.RecipeBookViewModel
import com.example.recipebook.ui.theme.RecipeBookTheme

@Composable
fun DetailScreen(viewModel: RecipeBookViewModel, itemId: String, modifier: Modifier = Modifier) {
    if (itemId != "" && itemId != null) {
        val currentItem = viewModel.allRecipes
            .observeAsState(Recipes(listOf())).value.recipes
            .first {
                it.uuid == itemId
            }

        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            LazyColumn {
                item {
                    Column(modifier = modifier) {
                        Text(
                            text = currentItem.name,
                            modifier = modifier
                                .wrapContentWidth(align = CenterHorizontally)
                                .paddingFromBaseline(bottom = 4.dp),
                            fontSize = 28.sp,
                            style = MaterialTheme.typography.h6
                        )
                        LazyRow(modifier = modifier.padding(vertical = 8.dp)) {
                            items(currentItem.images) { item ->
                                Image(
                                    painter = rememberAsyncImagePainter(item),
                                    contentDescription = null,
                                    modifier = modifier.fillParentMaxSize()
                                )
                            }
                        }
                        Row(
                            modifier = modifier
                                .padding(horizontal = 16.dp)
                                .wrapContentWidth(Alignment.Start)
                        ) {
                            for (i in 1..currentItem.difficulty) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = i.toString()
                                )
                            }
                        }
                        Text(
                            text = currentItem.description,
                            modifier = modifier
                                .wrapContentWidth(align = CenterHorizontally)
                                .padding(vertical = 16.dp)
                                .paddingFromBaseline(bottom = 4.dp),
                            style = MaterialTheme.typography.caption,
                            fontSize = 20.sp
                        )
                        Text(
                            text = currentItem.instructions,
                            modifier = modifier
                                .wrapContentWidth(align = CenterHorizontally)
                                .paddingFromBaseline(bottom = 16.dp),
                            style = MaterialTheme.typography.caption,
                            fontSize = 20.sp
                        )
                        Text(
                            text = currentItem.lastUpdated.toString(),
                            modifier = modifier
                                .wrapContentWidth(align = Alignment.Start)
                                .paddingFromBaseline(bottom = 4.dp),
                            style = MaterialTheme.typography.caption,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevDetail(modifier: Modifier = Modifier) {
    RecipeBookTheme() {
        Surface(modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            LazyColumn {
                item {
                    Column(modifier = modifier) {
                        Text(text = "Recipe",
                            modifier = modifier
                                .wrapContentWidth(align = CenterHorizontally)
                                .paddingFromBaseline(bottom = 4.dp),
                            fontSize = 28.sp,
                            style = MaterialTheme.typography.h6
                        )
                        LazyRow(modifier = modifier.padding(vertical = 8.dp)) {

                        }
                        Row(modifier = modifier
                            .padding(horizontal = 16.dp)
                            .wrapContentWidth(Alignment.Start)
                        ) {
                            for (i in 1..4) {
                                Icon(imageVector = Icons.Default.Star,
                                    contentDescription = i.toString()
                                )
                            }
                        }
                        Text(text = "Description about recipe :)",
                            modifier = modifier
                                .wrapContentWidth(align = CenterHorizontally)
                                .padding(vertical = 16.dp)
                                .paddingFromBaseline(bottom = 4.dp),
                            style = MaterialTheme.typography.caption,
                            fontSize = 20.sp
                        )
                        Text(text = "Instruction recipe. Can i do also?",
                            modifier = modifier
                                .wrapContentWidth(align = CenterHorizontally)
                                .paddingFromBaseline(bottom = 16.dp),
                            style = MaterialTheme.typography.caption,
                            fontSize = 20.sp
                        )
                        Text(text = "12.12.12",
                            modifier = modifier
                                .wrapContentWidth(align = Alignment.Start)
                                .paddingFromBaseline(bottom = 4.dp),
                            style = MaterialTheme.typography.caption,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}