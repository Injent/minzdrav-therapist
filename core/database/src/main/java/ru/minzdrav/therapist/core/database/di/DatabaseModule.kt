package ru.minzdrav.therapist.core.database.di

import android.content.Context
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteOpenHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.minzdrav.therapist.core.database.TherapistDatabase
import ru.minzdrav.therapist.core.database.dao.TherapistCallDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTherapistDatabase(
        @ApplicationContext context: Context,
        openHelperFactory: SupportSQLiteOpenHelper.Factory?
    ): TherapistDatabase = Room.databaseBuilder(
        context,
        TherapistDatabase::class.java,
        "therapist-database"
    )
        .openHelperFactory(openHelperFactory)
        .build()

    @Provides
    fun provideTherapistCallDao(
        database: TherapistDatabase
    ): TherapistCallDao = database.therapistCallDao()
}