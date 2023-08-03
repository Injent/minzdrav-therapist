package ru.minzdrav.therapist.core.common.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.minzdrav.therapist.core.common.util.AppDateFormatter
import ru.minzdrav.therapist.core.common.util.withLocale
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Qualifier

@Qualifier
annotation class Formatter(val name: String)

@Module
@InstallIn(SingletonComponent::class)
object DateModule {
    @Provides
    @Formatter(AppDateFormatter.NumericDateTime)
    fun provideNumericDateTimeFormatter(
        @ApplicationContext context: Context
    ) = DateTimeFormatter
        .ofPattern("dd.MM HH.mm")
        .withZone(ZoneId.systemDefault())
        .withLocale(context)
}