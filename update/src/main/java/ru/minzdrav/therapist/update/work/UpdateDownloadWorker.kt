package ru.minzdrav.therapist.update.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.minzdrav.therapist.core.common.AppDispatcher.IO
import ru.minzdrav.therapist.core.common.Dispatcher
import ru.minzdrav.therapist.update.data.FileDownloader
import ru.minzdrav.therapist.update.errors.InstallErrorCode
import ru.minzdrav.therapist.update.errors.InstallException
import ru.minzdrav.therapist.update.model.InstallState
import ru.minzdrav.therapist.update.model.InstallStatus
import java.io.File
import kotlin.time.Duration.Companion.seconds

@HiltWorker
class UpdateDownloadWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val fileDownloader: FileDownloader,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : CoroutineWorker(appContext, params) {

    private var state = InstallState.defaultInstance()

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return applicationContext.updateDownloadForegroundInfo(0L, 1L)
    }

    override suspend fun doWork(): Result = withContext(ioDispatcher) {
        suspend fun sendState(installState: InstallState) {
            setProgress(installState.asWorkData())
            setForeground(
                applicationContext.updateDownloadForegroundInfo(
                    installState.bytesDownloaded,
                    installState.totalBytesToDownload
                )
            )
        }

        val success = awaitAll(
            async { downloadApk() },
            async {
                do {
                    sendState(state)
                    delay(1L.seconds)
                } while (state.installStatus == InstallStatus.DOWNLOADING)

                true
            }
        ).any { it }

        if (success) {
            Result.success()
        } else {
            Result.failure()
        }
    }

    private suspend fun downloadApk(): Boolean {
        return try {
            fileDownloader.downloadFile(
                url = inputData.getString(KEY_APK_URL)
                    ?: error("$KEY_APK_URL input data not specified"),
                destination = File(
                    inputData.getString(KEY_FILE_PATH)
                        ?: error("$KEY_FILE_PATH input data not specified")
                ),
                listener = { bytesDownloaded, totalBytes ->
                    state = InstallState(
                        installStatus = InstallStatus.DOWNLOADING,
                        bytesDownloaded = bytesDownloaded,
                        totalBytesToDownload = totalBytes
                    )
                }
            )
            state = state.copy(
                installStatus = InstallStatus.DOWNLOADED
            )
            true
        } catch (e: InstallException) {
            e.printStackTrace()
            state = state.copy(
                installStatus = InstallStatus.FAILED,
                errorCode = InstallErrorCode.ERROR_DOWNLOAD
            )
            false
        }
    }

    companion object {
        const val KEY_APK_URL = "apk_url"
        const val KEY_FILE_PATH = "file_path"

        fun start(apkUrl: String, destination: File) = OneTimeWorkRequestBuilder<UpdateDownloadWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(UpdateDownloadWorkConstraints)
            .setInputData(workDataOf(
                KEY_APK_URL to apkUrl,
                KEY_FILE_PATH to destination.path
            ))
            .build()
    }
}