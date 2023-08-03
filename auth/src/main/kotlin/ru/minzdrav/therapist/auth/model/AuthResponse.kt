package ru.minzdrav.therapist.auth.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import ru.minzdrav.therapist.core.model.UserAuthorization

@Serializable
data class AuthResponse(
    val accessToken: String,
    val expiresIn: Instant,
    val refreshToken: String,
    val userId: Long
) {
    fun asUserAuthorization() = UserAuthorization(
        accessToken = accessToken,
        expiresIn = expiresIn,
        refreshToken = refreshToken,
        userId = userId
    )
}
