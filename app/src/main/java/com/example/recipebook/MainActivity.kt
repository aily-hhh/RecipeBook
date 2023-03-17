package com.example.recipebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.recipebook.navigation.SetupNavHost
import com.example.recipebook.ui.theme.RecipeBookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeBookTheme {
                val navController = rememberNavController()
                SetupNavHost(navController = navController)
            }
        }
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
fun HomeBody(modifier: Modifier = Modifier) {
    LazyVerticalGrid(verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier.fillMaxWidth(),
        columns = GridCells.Fixed(2),
    ) {
        // elements
    }
}

@Composable
fun HomeItem(modifier: Modifier = Modifier) {
    Surface(modifier = modifier,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = modifier) {
            Icon(imageVector = Icons.Default.Home,
                contentDescription = null,
                modifier = modifier
                    .size(160.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(text = "Заголовок",
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(4.dp),
                style = MaterialTheme.typography.h6,
                maxLines = 1
            )
            Text(text = "Описание",
                modifier = modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally),
                maxLines = 1,
                style = MaterialTheme.typography.caption
            )
        }
    }
}