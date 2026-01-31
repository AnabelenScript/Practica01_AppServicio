package com.example.mytracks.src.features.Auth.presentation.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mytracks.MainActivity
import com.example.mytracks.src.core.di.AppContainer
import com.example.mytracks.src.features.Auth.domain.usecases.LoginUseCase
import com.example.mytracks.src.features.Auth.presentation.viewmodels.LoginViewModel
import com.example.mytracks.src.features.Auth.presentation.viewmodels.LoginViewModelFactory
import kotlinx.coroutines.launch

class AuthCallback : ComponentActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uri: Uri? = intent?.data
        val code = uri?.getQueryParameter("code")

        val codeVerifier = getSharedPreferences("auth", MODE_PRIVATE)
            .getString("code_verifier", null)

        val appContainer = AppContainer(this)
        val loginUseCase = LoginUseCase(appContainer.authRepository)
        val factory = LoginViewModelFactory(application, loginUseCase)
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        if (code != null && codeVerifier != null) {
            loginViewModel.login(code, codeVerifier)
        }

        lifecycleScope.launch {
            loginViewModel.uiState.collect { state ->
                state.token?.let { token ->

                    val prefs = getSharedPreferences("auth", MODE_PRIVATE)
                    prefs.edit()
                        .putString("access_token", token.accessToken)
                        .apply()

                    val intent = Intent(this@AuthCallback, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
