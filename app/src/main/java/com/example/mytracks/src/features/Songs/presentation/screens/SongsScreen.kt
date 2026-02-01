package com.example.mytracks.src.features.Songs.presentation.screens

import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mytracks.src.features.Songs.presentation.components.AudioPlayer
import com.example.mytracks.src.features.Songs.presentation.components.SongCard
import com.example.mytracks.src.features.Songs.presentation.viewmodels.SongsViewModel

@Composable
fun SongsScreen(
    uiState: SongsUIState,
    token: String,
    viewModel: SongsViewModel
) {
    var currentStreamUrl by remember { mutableStateOf<String?>(null) }
    val playingTrackId by viewModel.playingTrackId.collectAsStateWithLifecycle()


    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE8EAF6),
            Color(0xFFF3E5F5),
            Color(0xFFFCE4EC)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        Column(modifier = Modifier.padding(vertical = 40.dp, horizontal = 10.dp),


        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Mis canciones",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color(0xFF2C3E50)
                )
            }

            when {
                uiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = Color(0xFF673AB7)
                        )
                    }
                }

                uiState.error != null -> {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFFFEBEE),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = "Error: ${uiState.error}",
                            color = Color(0xFFC62828),
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                else -> {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(uiState.songs) { track ->
                            SongCard(
                                track = track,
                                isPlaying = playingTrackId == track.id,
                                onPlayClick = {
                                    currentStreamUrl = track.stream_url
                                    viewModel.onSongClicked(track.id)
                                }
                            )
                        }
                    }

                    currentStreamUrl?.let { url ->
                        AudioPlayer(
                            streamUrl = url,
                            token = token
                        )
                    }
                }
            }
        }
    }
}