package ru.minzdrav.therapist.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.minzdrav.therapist.core.model.UserData
import ru.minzdrav.therapist.core.storage.DataVersions

interface UserDataRepository {
    val data: Flow<UserData>

    suspend fun setSyncRequired(isSyncRequired: Boolean)

    suspend fun getDataVersions(): DataVersions

    suspend fun setDataVersions(dataVersions: DataVersions)
}