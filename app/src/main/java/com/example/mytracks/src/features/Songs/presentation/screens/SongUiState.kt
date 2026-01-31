package com.example.mytracks.src.features.Songs.presentation.screens

import com.example.mytracks.src.features.Songs.domain.entities.Track

data class SongsUIState(
    val isLoading: Boolean = false,
    val songs: List<Track> = emptyList(),
    val error: String? = null
)
