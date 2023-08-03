package ru.minzdrav.therapist.core.storage

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.minzdrav.therapist.core.model.UserData

class UserPreferencesDataSource(private val datastore: DataStore<UserPreferences>) {
    val data = datastore.data.map {
        UserData(
            lastUser = it.lastUser.ifEmpty { null },
            callsListVersion = it.callsListVersion,
            syncRequired = it.syncRequired,
            isInitialized = it.getIsInitialized()
        )
    }

    suspend fun init() {
        if (datastore.data.first().getIsInitialized()) return

        datastore.updateData {
            it.copy {
                syncRequired = true
                isInitialized = true
            }
        }
    }

    private suspend fun getUserData() = data.first()

    suspend fun setSyncRequired(value: Boolean) {
        datastore.updateData {
            it.copy { syncRequired = value }
        }
    }

    suspend fun getDataVersions(): DataVersions = with(getUserData()) {
        DataVersions(
            calls = callsListVersion
        )
    }

    suspend fun setDataVersions(dataVersions: DataVersions) = with(dataVersions) {
        datastore.updateData {
            it.copy {
                callsListVersion = calls
            }
        }
    }
}