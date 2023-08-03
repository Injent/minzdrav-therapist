package ru.minzdrav.therapist.sync.helpers

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import ru.minzdrav.therapist.core.data.repository.UserDataRepository
import ru.minzdrav.therapist.core.data.util.SyncManager
import ru.minzdrav.therapist.sync.workers.SyncWorker
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncWorkManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userDataRepository: UserDataRepository
) : SyncManager {

    override val isSyncing: Flow<Boolean> =
        WorkManager.getInstance(context).getWorkInfosForUniqueWorkLiveData(SyncWorker.Name)
            .asFlow()
            .map(List<WorkInfo>::anyRunning)
            .conflate()

    override val isSyncRequired: Flow<Boolean>
        get() = userDataRepository.data.map { it.syncRequired }

    override fun requestSync() {
        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                SyncWorker.Name,
                ExistingWorkPolicy.KEEP,
                SyncWorker.start()
            )
    }

    override suspend fun setSyncRequired(value: Boolean) {
        userDataRepository.setSyncRequired(value)
    }
}

private fun List<WorkInfo>.anyRunning() = any { it.state == WorkInfo.State.RUNNING }