package ru.minzdrav.therapist.core.storage.di

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.google.crypto.tink.Aead
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import ru.minzdrav.therapist.core.common.AppDispatcher.IO
import ru.minzdrav.therapist.core.common.Dispatcher
import ru.minzdrav.therapist.core.security.crypto.di.SecurityModule
import ru.minzdrav.therapist.core.storage.UserPreferencesDataSource
import ru.minzdrav.therapist.core.storage.UserPreferencesSerializer
import ru.minzdrav.therapist.core.storage.encrypted.EncryptedDataSerializer
import ru.minzdrav.therapist.core.storage.encrypted.EncryptedDataSource
import javax.inject.Singleton

@Module(includes = [SecurityModule::class])
@InstallIn(SingletonComponent::class)
object StorageModule {
    private const val DATASTORE_FILE = "settings.pb"
    private const val ENCRYPTED_DATASTORE_FILE = "encrypted_storage.pb"

    @Provides
    @Singleton
    fun providesSettingsManager(
        @ApplicationContext context: Context,
        @Dispatcher(IO) ioDispatcher: CoroutineDispatcher
    ): UserPreferencesDataSource {
        val dataStore = DataStoreFactory.create(
            serializer = UserPreferencesSerializer,
            scope = CoroutineScope(ioDispatcher + SupervisorJob()),
        ) {
            context.dataStoreFile(DATASTORE_FILE)
        }
        return UserPreferencesDataSource(dataStore)
    }

    @Provides
    @Singleton
    fun providesEncryptedStorage(
        @ApplicationContext context: Context,
        @Dispatcher(IO) ioDispatcher: CoroutineDispatcher,
        aead: Aead
    ): EncryptedDataSource {
        val dataStore = DataStoreFactory.create(
            serializer = EncryptedDataSerializer(aead),
            scope = CoroutineScope(ioDispatcher + SupervisorJob())
        ) {
            context.dataStoreFile(ENCRYPTED_DATASTORE_FILE)
        }
        return EncryptedDataSource(dataStore)
    }
}