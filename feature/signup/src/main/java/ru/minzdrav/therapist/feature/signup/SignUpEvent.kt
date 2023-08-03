package ru.minzdrav.therapist.feature.signup

sealed interface SignUpEvent {
    data class ChangeFirstName(val value: String) : SignUpEvent
    data class ChangeLastName(val value: String) : SignUpEvent
    data class ChangeLogin(val value: String) : SignUpEvent
    data class ChangePassword(val value: String) : SignUpEvent
    object SignUp : SignUpEvent
}