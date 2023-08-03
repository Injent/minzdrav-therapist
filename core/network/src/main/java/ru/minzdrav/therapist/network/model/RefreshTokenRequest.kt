package ru.minzdrav.therapist.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    @SerialName("grant_type") val grantType: String,
    @SerialName("refresh_token") val refreshToken: String
)
