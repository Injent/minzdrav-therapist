package ru.minzdrav.therapist.sync.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.minzdrav.therapist.core.data.util.SyncManager
import ru.minzdrav.therapist.sync.helpers.SyncWorkManager

@Module
@InstallIn(SingletonComponent::class)
interface FlavoredSyncModule {
    @Binds
    fun SyncWorkManager.binds(): SyncManager
}