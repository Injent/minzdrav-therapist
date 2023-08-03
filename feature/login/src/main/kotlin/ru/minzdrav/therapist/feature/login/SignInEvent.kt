package ru.minzdrav.therapist.feature.login

sealed interface SignInEvent {
    data class ChangeLogin(val value: String) : SignInEvent
    data class ChangePassword(val value: String) : SignInEvent
    object SignIn : SignInEvent
    object CantLogin : SignInEvent
}