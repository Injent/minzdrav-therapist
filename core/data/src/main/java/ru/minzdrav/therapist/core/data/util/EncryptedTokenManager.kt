package ru.minzdrav.therapist.core.data.util

import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import ru.minzdrav.therapist.core.data.repository.EncryptedDataRepository
import ru.minzdrav.therapist.network.model.AuthToken
import ru.minzdrav.therapist.network.retrofit.TokenManager
import javax.inject.Inject

class EncryptedTokenManager @Inject constructor(
    private val encryptedDataRepository: EncryptedDataRepository
) : TokenManager {
    override suspend fun provideToken(): AuthToken? {
        val authorization = encryptedDataRepository.authorization.first()
            ?: return null
        return with(authorization) { AuthToken(accessToken, expiresIn, refreshToken) }
    }

    override suspend fun saveToken(authToken: AuthToken) {
        encryptedDataRepository.updateAuthToken(authToken.accessToken, authToken.expiresIn)
    }

    override suspend fun isExpired(): Boolean {
        return (provideToken()?.expiresIn ?: return true) <= Clock.System.now()
    }
}