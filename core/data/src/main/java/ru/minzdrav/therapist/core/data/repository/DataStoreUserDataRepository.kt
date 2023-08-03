package ru.minzdrav.therapist.core.data.repository

import ru.minzdrav.therapist.core.storage.DataVersions
import ru.minzdrav.therapist.core.storage.UserPreferencesDataSource
import javax.inject.Inject

class DataStoreUserDataRepository @Inject constructor(
    private val userPreferencesDataSource: UserPreferencesDataSource
) : UserDataRepository {
    override val data = userPreferencesDataSource.data

    override suspend fun setSyncRequired(isSyncRequired: Boolean) {
        userPreferencesDataSource.setSyncRequired(isSyncRequired)
    }

    override suspend fun getDataVersions(): DataVersions =
        userPreferencesDataSource.getDataVersions()

    override suspend fun setDataVersions(dataVersions: DataVersions) {
        userPreferencesDataSource.setDataVersions(dataVersions)
    }
}