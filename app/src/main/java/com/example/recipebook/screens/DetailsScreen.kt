package com.example.recipebook.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.recipebook.R
import coil.compose.rememberAsyncImagePainter
import com.example.recipebook.data.viewModels.RecipeBookViewModel
import com.example.recipebook.navigation.Screens
import com.example.recipebook.ui.theme.RecipeBookTheme
import com.example.recipebook.utils.HtmlText
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.absoluteValue

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class)
@ExperimentalMaterialApi
@Composable
fun DetailScreen(
    viewModel: RecipeBookViewModel,
    navController: NavController,
    itemId: String,
    modifier: Modifier = Modifier
) {
    if (itemId != "") {
        val currentItem = viewModel.allRecipes
            .observeAsState().value!!.recipes
            .first {
                it.uuid == itemId
            }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = modifier.fillMaxSize()
        ){
            item {
                Column(modifier = modifier) {
                    if (!currentItem.name.isNullOrEmpty()) {
                        Text(
                            text = currentItem.name,
                            modifier = modifier
                                .align(CenterHorizontally)
                                .paddingFromBaseline(bottom = 4.dp),
                            fontSize = 22.sp,
                            style = MaterialTheme.typography.h6
                        )
                    }
                    if (!currentItem.images.isNullOrEmpty()) {
                        ViewPagerSlider(
                            list = currentItem.images,
                            navController = navController,
                            modifier = modifier.padding(top = 4.dp)
                        )
                    }
                    Row(
                        modifier = modifier
                            .padding(bottom = 16.dp)
                            .align(Alignment.Start)
                    ) {
                        for (i in 1..currentItem.difficulty) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = i.toString()
                            )
                        }
                    }
                    if (!currentItem.description.isNullOrEmpty()) {
                        TextSection(
                            title = stringResource(id = R.string.description),
                            currentText = currentItem.description
                        )
                    }
                    if (!currentItem.instructions.isNullOrEmpty()) {
                        TextSection(
                            title = stringResource(id = R.string.instruction),
                            currentText = currentItem.instructions
                        )
                    }
                    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                    val instant = Instant.ofEpochMilli(currentItem.lastUpdated.toLong())
                    val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
                    Text(
                        text = formatter.format(date),
                        modifier = modifier
                            .paddingFromBaseline(bottom = 16.dp)
                            .padding(end = 8.dp)
                            .align(Alignment.End),
                        fontSize = 10.sp,
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}

@Composable
fun TextSection(modifier: Modifier = Modifier,
                title: String,
                currentText: String
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            modifier = modifier
                .align(CenterHorizontally)
                .paddingFromBaseline(bottom = 8.dp),
            style = MaterialTheme.typography.button,
            fontFamily = FontFamily.Monospace,
            fontSize = 12.sp
        )
        HtmlText(
            html = currentText,
            modifier = modifier
                .padding(bottom = 24.dp),
            textSize = (16).toFloat()
        )
    }
}

@ExperimentalPagerApi
@Composable
fun ViewPagerSlider(list: List<String>, navController: NavController, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(
        initialPage = 0
    )

    LaunchedEffect(Unit) {
        yield()
        delay(15000)
        pagerState.animateScrollToPage(
            page = (pagerState.currentPage + 1) % (list.size),
            animationSpec = tween(700)
        )
    }
    
    Column(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = modifier,
            count = list.size
        ) { page ->
            val newItem = list[page]
            Image(painter = rememberAsyncImagePainter(newItem),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .height(360.dp)
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(Screens.ImageScreen.route + "/$newItem")
                    }
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = modifier
                .align(CenterHorizontally)
                .padding(16.dp)
        )
    }
}