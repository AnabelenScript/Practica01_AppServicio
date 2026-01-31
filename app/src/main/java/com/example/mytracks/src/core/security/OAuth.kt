package com.example.mytracks.src.core.security

import android.util.Base64
import java.security.MessageDigest
import java.security.SecureRandom

fun generateCodeVerifier(): String {
    val secureRandom = SecureRandom()
    val code = ByteArray(32)
    secureRandom.nextBytes(code)
    return Base64.encodeToString(code, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
}

fun generateCodeChallenge(codeVerifier: String): String {
    val bytes = codeVerifier.toByteArray(Charsets.US_ASCII)
    val messageDigest = MessageDigest.getInstance("SHA-256")
    messageDigest.update(bytes)
    val digest = messageDigest.digest()
    return Base64.encodeToString(digest, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
}
