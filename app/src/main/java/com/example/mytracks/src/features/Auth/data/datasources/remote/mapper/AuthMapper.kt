package com.example.mytracks.src.features.Auth.data.datasources.remote.mapper
import com.example.mytracks.src.features.Auth.data.datasources.remote.model.AuthTokenDto
import com.example.mytracks.src.features.Auth.domain.entities.AuthToken

fun AuthTokenDto.toDomain(): AuthToken {
    return AuthToken(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        expiresIn = this.expiresIn
    )
}
