package ru.minzdrav.therapist.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.minzdrav.therapist.auth.retrofit
import ru.minzdrav.therapist.auth.data

@Module
@InstallIn(SingletonComponent::class)
interface FlavoredAuthModule {
    @Binds
    fun RetrofitAuthRepository.binds(): AuthRepository
}