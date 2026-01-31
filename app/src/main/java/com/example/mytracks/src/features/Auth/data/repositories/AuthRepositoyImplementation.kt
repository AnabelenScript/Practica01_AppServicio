package com.example.mytracks.src.features.Auth.data.repositories

import com.example.mytracks.src.core.network.SoundCloudApi
import com.example.mytracks.BuildConfig
import com.example.mytracks.src.features.Auth.data.datasources.remote.mapper.toDomain
import com.example.mytracks.src.features.Auth.domain.entities.AuthToken
import com.example.mytracks.src.features.Auth.domain.repositories.AuthRepository

class AuthRepositoryImplementation(
    private val api: SoundCloudApi
) : AuthRepository {


    override suspend fun login(code: String, codeVerifier: String): AuthToken {
        val dto = api.getToken(
            clientId = BuildConfig.SOUNDCLOUD_CLIENT_ID,
            clientSecret = BuildConfig.SOUNDCLOUD_CLIENT_SECRET,
            redirectUri = BuildConfig.SOUNDCLOUD_REDIRECT_URI,
            grantType = "authorization_code", code = code,
            codeVerifier = codeVerifier )
        return dto.toDomain() }

    override suspend fun refreshToken(refreshToken: String): AuthToken {
        val dto = api.getToken(
            clientId = BuildConfig.SOUNDCLOUD_CLIENT_ID,
            clientSecret = BuildConfig.SOUNDCLOUD_CLIENT_SECRET,
            redirectUri = BuildConfig.SOUNDCLOUD_REDIRECT_URI,
            grantType = "refresh_token", code = refreshToken,
            codeVerifier = ""
        )
        return dto.toDomain()
    }


    }

