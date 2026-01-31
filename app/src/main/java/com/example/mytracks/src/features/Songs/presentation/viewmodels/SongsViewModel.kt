package com.example.mytracks.src.features.Songs.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytracks.src.features.Songs.domain.repositories.SongsRepository
import com.example.mytracks.src.features.Songs.domain.usecases.GetSongsUseCase
import com.example.mytracks.src.features.Songs.presentation.screens.SongsUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class SongsViewModel(
    private val getSongsUseCase: GetSongsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SongsUIState())
    val uiState = _uiState.asStateFlow()
    private val _currentStream = MutableStateFlow<String?>(null)
    val currentStream = _currentStream.asStateFlow()


    fun loadSongs(token: String) {
        _uiState.value = SongsUIState(isLoading = true)
        viewModelScope.launch {
            runCatching { getSongsUseCase(token) }
                .onSuccess { tracks ->
                    _uiState.value = SongsUIState(isLoading = false, songs = tracks)
                }
                .onFailure { e ->
                    _uiState.value = SongsUIState(isLoading = false, error = e.message)
                }
        }
    }

}
