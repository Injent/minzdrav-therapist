package ru.minzdrav.therapist.network.model

import kotlinx.datetime.Instant

data class AuthToken(
    val accessToken: String,
    val expiresIn: Instant,
    val refreshToken: String
)
