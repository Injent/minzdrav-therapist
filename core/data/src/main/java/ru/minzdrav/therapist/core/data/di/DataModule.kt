package ru.minzdrav.therapist.core.data.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.minzdrav.therapist.core.data.repository.DataStoreEncryptedDataRepository
import ru.minzdrav.therapist.core.data.repository.DataStoreUserDataRepository
import ru.minzdrav.therapist.core.data.repository.EncryptedDataRepository
import ru.minzdrav.therapist.core.data.repository.UserDataRepository
import ru.minzdrav.therapist.core.data.util.ConnectivityManagerMonitor
import ru.minzdrav.therapist.core.data.util.EncryptedTokenManager
import ru.minzdrav.therapist.core.data.util.NetworkMonitor
import ru.minzdrav.therapist.network.retrofit.TokenManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsTokenManager(encryptedTokenManager: EncryptedTokenManager): TokenManager

    @Binds
    fun bindsDataStoreUserDataRepository(
        dataStoreUserDataRepository: DataStoreUserDataRepository
    ): UserDataRepository

    @Binds
    fun bindsDataStoreEncryptedDataRepository(
        dataStoreEncryptedDataRepository: DataStoreEncryptedDataRepository
    ): EncryptedDataRepository

    companion object {
        @Provides
        @Singleton
        fun providesNetworkMonitor(
            @ApplicationContext context: Context
        ): NetworkMonitor {
            return ConnectivityManagerMonitor(context)
        }
    }
}