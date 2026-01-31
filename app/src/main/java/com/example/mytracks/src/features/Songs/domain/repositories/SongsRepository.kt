package com.example.mytracks.src.features.Songs.domain.repositories

import com.example.mytracks.src.features.Auth.domain.entities.AuthToken
import com.example.mytracks.src.features.Songs.domain.entities.Track


interface SongsRepository {
    suspend fun getSongs(token: String): List<Track>

}
