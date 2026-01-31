package com.example.mytracks.src.features.Auth.domain.usecases

import com.example.mytracks.src.features.Auth.domain.entities.AuthToken
import com.example.mytracks.src.features.Auth.domain.repositories.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(code: String, codeVerifier: String): AuthToken {
        return repository.login(code, codeVerifier)
    }
}
