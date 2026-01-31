package com.example.mytracks.src.features.Auth.presentation.screens

import com.example.mytracks.src.features.Auth.domain.entities.AuthToken


data class AuthUIState(
    val isLoading: Boolean = false,
    val token: AuthToken? = null,
    val error: String? = null
)