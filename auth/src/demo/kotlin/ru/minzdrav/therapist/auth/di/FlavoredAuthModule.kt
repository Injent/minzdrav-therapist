package ru.minzdrav.therapist.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.minzdrav.therapist.auth.data.AuthRepository
import ru.minzdrav.therapist.auth.fake.FakeAuthRepository

@Module
@InstallIn(SingletonComponent::class)
interface FlavoredAuthModule {
    @Binds
    fun FakeAuthRepository.binds(): AuthRepository
}