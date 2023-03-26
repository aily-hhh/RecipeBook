package com.example.recipebook.screens

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.example.recipebook.R
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageScreen(itemUrl: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val downloadState = rememberSaveable {
        mutableStateOf(false)
    }

    Column(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = downloadState.value) {
            Row(
                modifier = modifier.padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                OutlinedButton(
                    onClick = {
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                        {
                            if(ContextCompat.checkSelfPermission(
                                    context, Manifest.permission.WRITE_EXTERNAL_STORAGE
                                ) == PackageManager.PERMISSION_GRANTED)
                            {
                                downloading(itemUrl, itemUrl, context)
                                Toast.makeText(context, "Downloaded", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            downloading(itemUrl, itemUrl, context)
                            Toast.makeText(context, "Downloaded", Toast.LENGTH_SHORT).show()
                        }
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
                    painter = rememberAsyncImagePainter(URLDecoder.decode(itemUrl,  StandardCharsets.UTF_8.toString())),
                    contentDescription = "Image"
                )
            }
        }
    }
}

fun downloading(imageLink:String, title:String, context: Context){
    val request = DownloadManager.Request(Uri.parse(imageLink))
    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
    request.setTitle(title)
    request.setDescription("Downloading Image")
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    request.setDestinationInExternalPublicDir(
        Environment.DIRECTORY_DOWNLOADS,
        "${System.currentTimeMillis()}")
    val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    manager.enqueue(request)

}