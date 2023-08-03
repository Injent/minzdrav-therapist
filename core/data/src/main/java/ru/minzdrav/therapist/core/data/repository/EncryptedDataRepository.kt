package ru.minzdrav.therapist.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import ru.minzdrav.therapist.core.model.UserAuthorization

interface EncryptedDataRepository {
    val authorization: Flow<UserAuthorization?>
    suspend fun updateAuthToken(accessToken: String, expiresIn: Instant)
    suspend fun setUserAuthorization(authorization: UserAuthorization)
}