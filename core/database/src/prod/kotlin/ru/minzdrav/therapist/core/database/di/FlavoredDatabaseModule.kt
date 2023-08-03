package ru.minzdrav.therapist.core.database.di

import androidx.sqlite.db.SupportSQLiteOpenHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SupportFactory
import ru.minzdrav.therapist.core.security.CryptoManager

@Module
@InstallIn(SingletonComponent::class)
object FlavoredDatabaseModule {
    @Provides
    fun provideOpenHelperFactory(
        cryptoManager: CryptoManager
    ): SupportSQLiteOpenHelper.Factory {
        return SupportFactory(cryptoManager.getEncodedKey())
    }
}