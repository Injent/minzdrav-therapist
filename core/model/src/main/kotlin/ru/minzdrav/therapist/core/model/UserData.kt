package ru.minzdrav.therapist.core.model

data class UserData(
    val lastUser: String? = null,
    val callsListVersion: Int = 0,
    val syncRequired: Boolean = true,
    val isInitialized: Boolean = false
)