package ru.minzdrav.therapist.update.manager

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import androidx.lifecycle.asFlow
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import ru.minzdrav.therapist.update.data.provider.UpdateInfoProvider
import ru.minzdrav.therapist.update.model.AppUpdateInfo
import ru.minzdrav.therapist.update.model.InstallState
import ru.minzdrav.therapist.update.work.UpdateDownloadWorkName
import ru.minzdrav.therapist.update.work.UpdateDownloadWorker
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TherapistAppAppUpdateManager @Inject constructor(
    @ApplicationContext private val context: Context,
    updateInfoProvider: UpdateInfoProvider,
) : AppUpdateManager {
    private val updateApkFilePath: String
        get() = "${context.cacheDir}/update.apk"

    override val updateInfo: Flow<AppUpdateInfo?> = updateInfoProvider.appUpdateInfo

    override val installState: Flow<InstallState> =
        WorkManager.getInstance(context)
            .getWorkInfosForUniqueWorkLiveData(UpdateDownloadWorkName)
            .asFlow()
            .map {
                it?.firstOrNull()?.progress?.let {
                    data -> InstallState(data)
                } ?: InstallState.defaultInstance()
            }
            .conflate()

    override fun downloadAppUpdate() {
        startDownloadUpdateWork(
            apkUrl = "",
            destination = File(updateApkFilePath),
        )
    }

    override fun installAppUpdate() {
        val updateApkFilePath = "${context.cacheDir}/update.apk"
        val packageName = context.packageName
        val packageInstaller = context.packageManager.packageInstaller

        val params = PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL)
        params.setAppPackageName(packageName)

        val sessionId = packageInstaller.createSession(params)
        val session = packageInstaller.openSession(sessionId)

        val input = File(updateApkFilePath).inputStream()
        val output = session.openWrite(packageName, 0, input.available().toLong())

        input.copyTo(output)
        input.close()

        session.fsync(output)
        output.close()

        session.commit(
            PendingIntent.getBroadcast(
                context,
                sessionId,
                Intent("com.your.package.ACTION_CUSTOM_BROADCAST"),
                PendingIntent.FLAG_IMMUTABLE,
            ).intentSender,
        )
    }

    private fun startDownloadUpdateWork(apkUrl: String, destination: File) {
        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                UpdateDownloadWorkName,
                ExistingWorkPolicy.KEEP,
                UpdateDownloadWorker.start(apkUrl, destination),
            )
    }
}