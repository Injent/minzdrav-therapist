package ru.minzdrav.therapist.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.minzdrav.therapist.core.data.repository.ServerDrivenUiRepository
import ru.minzdrav.therapist.core.data.repository.TherapistCallRepository
import ru.minzdrav.therapist.core.data.repository.fake.FakeServerDrivenUiRepository
import ru.minzdrav.therapist.core.data.repository.fake.FakeTherapistCallRepository

@Module
@InstallIn(SingletonComponent::class)
interface FlavoredDataModule {

    @Binds
    fun FakeTherapistCallRepository.binds(): TherapistCallRepository

    @Binds
    fun bindsServerDrivenUiRepository(
        fakeServerDrivenUiRepository: FakeServerDrivenUiRepository
    ): ServerDrivenUiRepository
}