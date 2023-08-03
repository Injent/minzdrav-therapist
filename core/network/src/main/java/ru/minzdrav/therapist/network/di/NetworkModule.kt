package ru.minzdrav.therapist.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.minzdrav.therapist.core.network.BuildConfig.DEBUG
import ru.minzdrav.therapist.network.retrofit.RetrofitAuthTokenInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(
        authTokenInterceptor: RetrofitAuthTokenInterceptor
    ): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    if (DEBUG) setLevel(HttpLoggingInterceptor.Level.BODY)
                }
        )
        .addInterceptor(authTokenInterceptor)
        .build()
}