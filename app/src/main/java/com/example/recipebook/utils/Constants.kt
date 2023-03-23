package com.example.recipebook.utils

import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat

class Constants {
    object Screens {
        const val MAIN_SCREEN = "main_screen"
        const val DETAILS_SCREEN = "details_screen"
        const val IMAGE_SCREEN = "image_screen"
    }
}

@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier, textSize: Float) {
    AndroidView(
        modifier = modifier,
        factory = {
            TextView(it)
        },
        update = {
            it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
            it.textSize = textSize
        }
    )
}