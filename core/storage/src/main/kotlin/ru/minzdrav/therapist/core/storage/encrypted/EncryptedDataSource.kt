package ru.minzdrav.therapist.core.storage.encrypted

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import ru.minzdrav.therapist.core.model.EncryptedUserData
import ru.minzdrav.therapist.core.model.UserAuthorization
import ru.minzdrav.therapist.core.storage.EncryptedData
import ru.minzdrav.therapist.core.storage.copy

class EncryptedDataSource internal constructor (private val datastore: DataStore<EncryptedData>) {
    val data: Flow<EncryptedUserData> = datastore.data
        .map {
            EncryptedUserData(
                offlineLogin = it.offlineLogin,
                offlinePassword = it.offlinePassword
            )
        }

    val userAuthorization: Flow<UserAuthorization?> = datastore.data
        .map {
            with(it) {
                if (accessToken.isNullOrEmpty()) return@map null

                UserAuthorization(
                    accessToken = accessToken,
                    expiresIn = Instant.parse(expiresIn),
                    refreshToken = refreshToken,
                    userId = userId
                )
            }
        }

    suspend fun updateAuthToken(accessToken: String, expiresIn: Instant) {
        datastore.updateData {
            it.copy {
                this.accessToken = accessToken
                this.expiresIn = expiresIn.toString()
            }
        }
    }

    suspend fun setUserAuthorization(value: UserAuthorization) {
        datastore.updateData {
            it.copy {
                accessToken = value.accessToken
                expiresIn = value.expiresIn.toString()
                refreshToken = value.refreshToken
                userId = value.userId
            }
        }
    }

    suspend fun clear() {
        datastore.updateData { EncryptedData.getDefaultInstance() }
    }
}