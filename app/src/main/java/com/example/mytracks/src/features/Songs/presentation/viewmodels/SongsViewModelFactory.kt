package com.example.mytracks.src.features.Songs.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mytracks.src.features.Songs.domain.repositories.SongsRepository
import com.example.mytracks.src.features.Songs.domain.usecases.GetSongsUseCase


class SongsViewModelFactory(
    private val getSongsUseCase: GetSongsUseCase,

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SongsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SongsViewModel(getSongsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
