package ru.minzdrav.therapist.core.model

data class EncryptedUserData(
    val offlineLogin: String,
    val offlinePassword: String
)
