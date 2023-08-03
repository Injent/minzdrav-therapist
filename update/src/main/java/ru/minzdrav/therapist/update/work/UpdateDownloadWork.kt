package ru.minzdrav.therapist.update.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build.VERSION.SDK_INT
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.ForegroundInfo
import androidx.work.NetworkType
import ru.minzdrav.therapist.update.R
import kotlin.math.roundToInt

internal const val UpdateDownloadWorkName = "update_work"
private const val UpdateDownloadNotificationId = 0
private const val UpdateDownloadChannelId = "update_download_channel"
private const val MAX_PROGRESS = 100

internal val UpdateDownloadWorkConstraints = Constraints.Builder()
    .setRequiredNetworkType(NetworkType.CONNECTED)
    .build()

internal fun Context.updateDownloadForegroundInfo(
    bytesDownloaded: Long,
    totalBytesToDownload: Long
): ForegroundInfo {
    if (SDK_INT >= 26) {
        val channel = NotificationChannel(
            UpdateDownloadChannelId,
            getString(R.string.notification_channel_update_download_name),
            NotificationManager.IMPORTANCE_MIN,
        )

        val notificationManager: NotificationManager? =
            getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        notificationManager?.createNotificationChannel(channel)
    }

    val downloadedBytesPercentage = if (bytesDownloaded != 0L || totalBytesToDownload != 0L) {
        ((bytesDownloaded.toDouble() / totalBytesToDownload.toDouble()) * MAX_PROGRESS).roundToInt()
    } else 0

    val notification = NotificationCompat.Builder(
        this,
        UpdateDownloadChannelId,
    )
        .setSmallIcon(R.mipmap.ic_launcher_foreground)
        .setSilent(true)
        .setContentTitle(getString(R.string.notification_channel_update_download_name))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setProgress(MAX_PROGRESS, downloadedBytesPercentage, false)
        .build()

    return ForegroundInfo(
        UpdateDownloadNotificationId,
        notification,
    )
}