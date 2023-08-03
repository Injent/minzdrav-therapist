package ru.minzdrav.therapist.core.domain.model

enum class AuthStatus {
    AUTHED,
    AUTHED_OFFLINE,
    NOT_AUTHED,
    PENDING
}