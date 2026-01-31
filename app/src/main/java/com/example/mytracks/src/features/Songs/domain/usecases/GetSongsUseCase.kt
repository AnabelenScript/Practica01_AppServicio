package com.example.mytracks.src.features.Songs.domain.usecases

import com.example.mytracks.src.features.Songs.domain.entities.Track
import com.example.mytracks.src.features.Songs.domain.repositories.SongsRepository

class GetSongsUseCase(
    private val repository: SongsRepository
) {
    suspend operator fun invoke(token: String): List<Track> {
        return repository.getSongs(token)
    }
}
