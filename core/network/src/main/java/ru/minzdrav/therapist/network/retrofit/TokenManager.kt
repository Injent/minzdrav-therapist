package ru.minzdrav.therapist.network.retrofit

import ru.minzdrav.therapist.network.model.AuthToken

interface TokenManager {
    suspend fun provideToken(): AuthToken?
    suspend fun saveToken(authToken: AuthToken)
    suspend fun isExpired(): Boolean
}