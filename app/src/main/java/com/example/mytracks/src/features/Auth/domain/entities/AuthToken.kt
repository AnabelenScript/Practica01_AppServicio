package com.example.mytracks.src.features.Auth.domain.entities

data class AuthToken(
    val accessToken: String,
    val refreshToken: String?,
    val expiresIn: Long
)
