package ru.minzdrav.therapist.core.data.util

import ru.minzdrav.therapist.core.storage.DataVersions

interface Synchronizer {
    suspend fun Syncable.sync() = this.syncWith(this@Synchronizer)

    suspend fun getDataVersions(): DataVersions

    suspend fun updateDataVersions(dataVersions: DataVersions)
}

interface Syncable {
    suspend fun syncWith(synchronizer: Synchronizer): Boolean
}