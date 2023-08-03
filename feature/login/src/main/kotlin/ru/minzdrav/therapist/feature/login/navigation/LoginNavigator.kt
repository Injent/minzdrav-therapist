package ru.minzdrav.therapist.feature.login.navigation

interface LoginNavigator {
    fun navigateToGeneral()
    fun navigateToSignUp()
    fun navigateToSignIn()
    fun navigateUp()
}