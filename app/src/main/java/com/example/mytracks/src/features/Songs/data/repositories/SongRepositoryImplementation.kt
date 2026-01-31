package com.example.mytracks.src.features.Songs.data.repositories

import android.util.Log
import com.example.mytracks.src.core.network.SoundCloudApi
import com.example.mytracks.src.features.Songs.domain.entities.Track
import com.example.mytracks.src.features.Songs.domain.repositories.SongsRepository


class SongsRepositoryImplementation(
    private val api: SoundCloudApi
) : SongsRepository {
    override suspend fun getSongs(token: String): List<Track> {
        val dtos = api.getMyTracks("Bearer $token")
        Log.d("SongsRepo", "Tracks recibidos: ${dtos.size}")
        dtos.forEach {
            Log.d("SongsRepo", "DTO -> id=${it.id}, title=${it.title}, duration=${it.duration}, artwork=${it.artwork_url}. stream_url=${it.stream_url}")
        }

        return dtos.map { Track(
            it.id,
            it.title,
            it.artwork_url,
            it.duration,
        it.stream_url,
        it.access
        )
        }
    }


}
