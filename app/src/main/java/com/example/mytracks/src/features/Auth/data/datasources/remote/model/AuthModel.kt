package com.example.mytracks.src.features.Auth.data.datasources.remote.model

import com.google.gson.annotations.SerializedName

data class AuthTokenDto(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String?,
    @SerializedName("expires_in") val expiresIn: Long
)
