package ru.minzdrav.therapist.network.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val accessToken: String,
    val expiresIn: Instant
)
