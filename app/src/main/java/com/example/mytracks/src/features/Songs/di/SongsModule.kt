package com.example.mytracks.src.features.Songs.di


import com.example.mytracks.src.core.di.AppContainer
import com.example.mytracks.src.features.Songs.domain.repositories.SongsRepository
import com.example.mytracks.src.features.Songs.domain.usecases.GetSongsUseCase
import com.example.mytracks.src.features.Songs.presentation.viewmodels.SongsViewModelFactory

class SongsModule(
    private val appContainer: AppContainer
) {

    private fun provideGetSongsUseCase(): GetSongsUseCase {
        return GetSongsUseCase(appContainer.songsRepository)
    }



    fun provideSongsViewModelFactory(): SongsViewModelFactory {
        return SongsViewModelFactory(
            getSongsUseCase = provideGetSongsUseCase(),

        )
    }

}
