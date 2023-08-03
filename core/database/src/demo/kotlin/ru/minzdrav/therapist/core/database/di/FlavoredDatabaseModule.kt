package ru.minzdrav.therapist.core.database.di

import androidx.sqlite.db.SupportSQLiteOpenHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FlavoredDatabaseModule {
    @Provides
    fun provideOpenHelperFactory(): SupportSQLiteOpenHelper.Factory? = null
}