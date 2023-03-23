package com.example.recipebook.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.recipebook.R


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageScreen(itemUrl: String, modifier: Modifier = Modifier) {
    val downloadState = rememberSaveable {
        mutableStateOf(false)
    }

    Column(modifier = modifier.fillMaxSize()) {
        if (downloadState.value) {
            Row(
                modifier = modifier.padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = {
                        Log.d("checkData", "Downloads")
                    }
                ) {
                    Text(text = stringResource(R.string.download))
                }
            }
        }
        Card(
            modifier = modifier.fillMaxSize(),
            onClick = {
                downloadState.value = !downloadState.value
            }
        ) {
            if (itemUrl == "") {
                Image(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Image"
                )
            } else {
                Image(
                    painter = rememberAsyncImagePainter(itemUrl),
                    contentDescription = "Image"
                )
            }
        }
    }
}
