package com.example.mytracks

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.mytracks.src.core.ui.theme.MyTracksTheme
import com.example.mytracks.src.features.Auth.domain.usecases.LoginUseCase
import com.example.mytracks.src.features.Auth.presentation.screens.LoginScreen
import com.example.mytracks.src.features.Auth.presentation.viewmodels.LoginViewModel
import com.example.mytracks.src.features.Auth.presentation.viewmodels.LoginViewModelFactory
import com.example.mytracks.src.core.di.AppContainer
import com.example.mytracks.src.features.Songs.di.SongsModule
import com.example.mytracks.src.features.Songs.presentation.screens.SongsScreen
import com.example.mytracks.src.features.Songs.presentation.viewmodels.SongsViewModel
import com.example.mytracks.src.features.Songs.presentation.viewmodels.SongsViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var songsViewModel: SongsViewModel
    lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        appContainer = AppContainer(this)

        val loginUseCase = LoginUseCase(appContainer.authRepository)
        val factory = LoginViewModelFactory(application, loginUseCase)
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]


        val songsModule = SongsModule(appContainer)
        val songsFactory = songsModule.provideSongsViewModelFactory()
        songsViewModel = ViewModelProvider(this, songsFactory)[SongsViewModel::class.java]


        setContent {
            MyTracksTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {


                    val prefs = getSharedPreferences("auth", MODE_PRIVATE)
                    val savedToken = prefs.getString("access_token", null)
                    val uiState by loginViewModel.uiState.collectAsState()

                    uiState.token?.let { token ->
                        prefs.edit().putString("access_token", token.accessToken).apply()
                        Log.d("Auth", "Token guardado: ${token.accessToken.take(10)}...")
                    }

                    val activeToken = uiState.token?.accessToken ?: savedToken

                    if (activeToken != null) {
                        val songsUiState by songsViewModel.uiState.collectAsState()
                        LaunchedEffect(activeToken) {
                            songsViewModel.loadSongs(activeToken)
                        }
                        SongsScreen(
                            uiState = songsUiState,
                            token = activeToken
                        )

                    } else {

                        LoginScreen(
                            uiState = uiState,
                            onLoginClick = { codeVerifier ->
                                uiState.token?.let { token ->
                                    prefs.edit().putString("access_token", token.accessToken).apply()
                                    songsViewModel.loadSongs(token.accessToken)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
