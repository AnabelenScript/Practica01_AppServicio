package com.example.mytracks.src.features.Auth.domain.repositories

import com.example.mytracks.src.features.Auth.domain.entities.AuthToken

interface AuthRepository {
    suspend fun login(code: String, codeVerifier: String): AuthToken
    suspend fun refreshToken(refreshToken: String): AuthToken
}