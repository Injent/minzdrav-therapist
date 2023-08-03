package ru.minzdrav.therapist.core.model

import kotlinx.datetime.Instant

data class UserAuthorization(
    val accessToken: String,
    val expiresIn: Instant,
    val refreshToken: String,
    val userId: Long
)
