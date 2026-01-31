package com.example.mytracks.src.features.Songs.data.datasources.remote.model

data class TrackDto(
    val id: Long,
    val title: String,
    val artwork_url: String?,
    val duration: Int,
    val stream_url: String?,
    val access: String?
)
