package com.example.mytracks.src.core.network
import com.example.mytracks.src.features.Auth.data.datasources.remote.model.AuthTokenDto
import retrofit2.http.*
import com.example.mytracks.src.features.Songs.data.datasources.remote.model.TrackDto

interface SoundCloudApi {

    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun getToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("code") code: String,
        @Field("code_verifier") codeVerifier: String
    ): AuthTokenDto

    @GET("me/tracks")
    suspend fun getMyTracks(
        @Header("Authorization") authorization: String
    ): List<TrackDto>


}

