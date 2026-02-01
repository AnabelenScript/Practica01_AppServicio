package com.example.mytracks.src.features.Songs.presentation.components

import android.media.browse.MediaBrowser
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory

@OptIn(UnstableApi::class)
@Composable

fun AudioPlayer(streamUrl: String, token: String) {
    val context = LocalContext.current

    val dataSourceFactory = remember(token) {
        DefaultHttpDataSource.Factory()
            .setDefaultRequestProperties(
                mapOf("Authorization" to "Bearer $token")
            )
    }

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(
                DefaultMediaSourceFactory(dataSourceFactory)
            )
            .build()
    }

    DisposableEffect(streamUrl) {
        val mediaItem = MediaItem.fromUri(streamUrl)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()

        onDispose {
            exoPlayer.release()
        }
    }
}
