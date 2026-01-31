package com.example.mytracks.src.features.Auth.presentation.screens

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import com.example.mytracks.BuildConfig
import com.example.mytracks.src.core.security.generateCodeChallenge
import com.example.mytracks.src.core.security.generateCodeVerifier

@Composable
fun LoginScreen(
    uiState: AuthUIState,
    onLoginClick: (codeVerifier: String) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        }

        uiState.error?.let {
            Text("Error: $it", color = MaterialTheme.colorScheme.error)
        }

        uiState.token?.let {
            Text("Login exitoso, token: ${it.accessToken.take(10)}...")
        }

        val prefs = context.getSharedPreferences("auth", MODE_PRIVATE)
        val token = prefs.getString("access_token", null)

        if (token == null) {
            val codeVerifier = generateCodeVerifier()
            val codeChallenge = generateCodeChallenge(codeVerifier)

            prefs.edit().putString("code_verifier", codeVerifier).apply()

            val authUrl = Uri.parse(
                "https://soundcloud.com/connect" +
                        "?client_id=${BuildConfig.SOUNDCLOUD_CLIENT_ID}" +
                        "&redirect_uri=${BuildConfig.SOUNDCLOUD_REDIRECT_URI}" +
                        "&response_type=code" +
                        "&scope=non-expiring" +
                        "&code_challenge=$codeChallenge" +
                        "&code_challenge_method=S256" +
                        "&state=anitauwu" +
                        "&display=popup"
            )
            val intent = Intent(Intent.ACTION_VIEW, authUrl)
            context.startActivity(intent)
        } else {
            Column {
                Text("Ya estás loggeado, ijo")
            }
        }
    }
}
