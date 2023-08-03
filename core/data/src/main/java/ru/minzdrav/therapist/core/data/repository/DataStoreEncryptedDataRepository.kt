package ru.minzdrav.therapist.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import ru.minzdrav.therapist.core.model.UserAuthorization
import ru.minzdrav.therapist.core.storage.encrypted.EncryptedDataSource
import javax.inject.Inject

class DataStoreEncryptedDataRepository @Inject constructor(
    private val encryptedDataSource: EncryptedDataSource
) : EncryptedDataRepository {

    override val authorization: Flow<UserAuthorization?> = encryptedDataSource.userAuthorization

    override suspend fun updateAuthToken(accessToken: String, expiresIn: Instant) {
        encryptedDataSource.updateAuthToken(accessToken, expiresIn)
    }

    override suspend fun setUserAuthorization(authorization: UserAuthorization) {
        encryptedDataSource.setUserAuthorization(authorization)
    }
}