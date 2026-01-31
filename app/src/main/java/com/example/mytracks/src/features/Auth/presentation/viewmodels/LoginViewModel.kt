package com.example.mytracks.src.features.Auth.presentation.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytracks.src.features.Auth.domain.usecases.LoginUseCase
import com.example.mytracks.src.features.Auth.presentation.screens.AuthUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    application: Application,
    private val loginUseCase: LoginUseCase
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(AuthUIState())
    val uiState = _uiState.asStateFlow()

    fun login(code: String, codeVerifier: String) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = runCatching { loginUseCase(code, codeVerifier) }
            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { token ->
                        // 🔹 Guardar token persistente
                        val prefs = getApplication<Application>()
                            .getSharedPreferences("auth", Context.MODE_PRIVATE)
                        prefs.edit().putString("access_token", token.accessToken).apply()

                        Log.d("Auth", "Token guardado: ${token.accessToken.take(10)}...")

                        currentState.copy(isLoading = false, token = token, error = null)
                    },
                    onFailure = { error ->
                        currentState.copy(isLoading = false, error = error.message)
                    }
                )
            }
        }
    }
}

