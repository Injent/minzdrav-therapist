package ru.minzdrav.therapist.core.data.util

import kotlinx.coroutines.flow.Flow

interface SyncManager {
    val isSyncing: Flow<Boolean>
    val isSyncRequired: Flow<Boolean>
    fun requestSync()
    suspend fun setSyncRequired(value: Boolean)
}