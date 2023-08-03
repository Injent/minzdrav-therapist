package ru.minzdrav.therapist.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.minzdrav.therapist.network.TherapistNetworkDataSource
import ru.minzdrav.therapist.network.fake.FakeTherapistNetworkDataSource

@Module
@InstallIn(SingletonComponent::class)
interface FlavoredNetworkModule {

    @Binds
    fun FakeTherapistNetworkDataSource.binds(): TherapistNetworkDataSource
}