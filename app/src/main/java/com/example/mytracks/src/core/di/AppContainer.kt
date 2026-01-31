package com.example.mytracks.src.core.di

import android.content.Context
import com.example.mytracks.src.core.network.SoundCloudApi
import com.example.mytracks.src.features.Auth.data.repositories.AuthRepositoryImplementation
import com.example.mytracks.src.features.Auth.domain.repositories.AuthRepository
import com.example.mytracks.src.features.Songs.data.repositories.SongsRepositoryImplementation
import com.example.mytracks.src.features.Songs.domain.repositories.SongsRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {
    private val soundCloudAuthRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://secure.soundcloud.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val soundCloudApiRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.soundcloud.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val soundCloudAuthApi: SoundCloudApi by lazy {
        soundCloudAuthRetrofit.create(SoundCloudApi::class.java)
    }

    val soundCloudTracksApi: SoundCloudApi by lazy {
        soundCloudApiRetrofit.create(SoundCloudApi::class.java)
    }

    val authRepository: AuthRepository by lazy {
        AuthRepositoryImplementation(soundCloudAuthApi)
    }

    val songsRepository: SongsRepository by lazy {
        SongsRepositoryImplementation(soundCloudTracksApi)
    }
}
