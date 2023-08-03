package ru.minzdrav.therapist.sync.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import ru.minzdrav.therapist.core.common.AppDispatcher.IO
import ru.minzdrav.therapist.core.common.Dispatcher
import ru.minzdrav.therapist.core.data.repository.TherapistCallRepository
import ru.minzdrav.therapist.core.data.repository.UserDataRepository
import ru.minzdrav.therapist.core.data.util.Synchronizer
import ru.minzdrav.therapist.core.storage.DataVersions
import ru.minzdrav.therapist.sync.helpers.SyncConstraints
import ru.minzdrav.therapist.sync.helpers.syncForegroundInfo

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val therapistCallRepository: TherapistCallRepository,
    private val userDataRepository: UserDataRepository
) : CoroutineWorker(appContext, params), Synchronizer {

    override suspend fun getForegroundInfo(): ForegroundInfo =
        applicationContext.syncForegroundInfo()

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        val syncedSuccessfully = awaitAll(
            async { therapistCallRepository.sync() }
        ).all { it }

        if (syncedSuccessfully) {
            userDataRepository.setSyncRequired(false)
            Result.success()
        } else {
            Result.retry()
        }
    }

    override suspend fun getDataVersions(): DataVersions = userDataRepository.getDataVersions()

    override suspend fun updateDataVersions(dataVersions: DataVersions) {
        userDataRepository.setDataVersions(dataVersions)
    }

    companion object {
        const val Name = "sync_work"
        fun start() = OneTimeWorkRequestBuilder<SyncWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(SyncConstraints)
            .build()
    }
}