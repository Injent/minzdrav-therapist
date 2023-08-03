package ru.minzdrav.therapist.update.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.minzdrav.therapist.update.data.FileDownloader
import ru.minzdrav.therapist.update.data.fake.FakeFileDownloader
import ru.minzdrav.therapist.update.data.fake.FakeUpdateInfoProvider
import ru.minzdrav.therapist.update.data.provider.UpdateInfoProvider
import ru.minzdrav.therapist.update.manager.AppUpdateManager
import ru.minzdrav.therapist.update.manager.TherapistAppAppUpdateManager

@Module
@InstallIn(SingletonComponent::class)
interface FlavoredUpdateModule {
    @Binds
    fun bindsFakeFileDownloader(
        fakeFileDownloader: FakeFileDownloader
    ): FileDownloader

    @Binds
    fun bindsFakeUpdateInfoProvider(
        fakeUpdateInfoProvider: FakeUpdateInfoProvider
    ): UpdateInfoProvider

    @Binds
    fun bindsAppUpdateManager(
        therapistAppAppUpdateManager: TherapistAppAppUpdateManager
    ) : AppUpdateManager
}