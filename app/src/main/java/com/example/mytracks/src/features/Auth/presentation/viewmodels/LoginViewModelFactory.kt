package com.example.mytracks.src.features.Auth.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mytracks.src.features.Auth.domain.usecases.LoginUseCase

class LoginViewModelFactory(
    private val application: Application,
    private val loginUseCase: LoginUseCase
) : ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(application, loginUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

