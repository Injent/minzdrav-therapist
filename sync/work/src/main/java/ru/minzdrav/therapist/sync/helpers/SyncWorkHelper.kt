package ru.minzdrav.therapist.sync.helpers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build.VERSION.SDK_INT
import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.ForegroundInfo
import androidx.work.NetworkType
import ru.minzdrav.therapist.sync.R

private const val SyncNotificationId = 0
private const val SyncChannelId = "SyncChannel"

internal val SyncConstraints
    get() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

internal fun Context.syncForegroundInfo(): ForegroundInfo {
    if (SDK_INT >= 26) {
        val channel = NotificationChannel(
            SyncChannelId,
            getString(R.string.notification_channel_sync_name),
            NotificationManager.IMPORTANCE_MIN,
        )

        val notificationManager: NotificationManager? =
            getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        notificationManager?.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(
        this,
        SyncChannelId,
    )
        .setSmallIcon(R.mipmap.ic_launcher_foreground)
        .setSilent(true)
        .setAutoCancel(true)
        .setContentTitle(getString(R.string.sync_notification_title))
        .setPriority(NotificationCompat.PRIORITY_MIN)
        .build()

    return ForegroundInfo(
        SyncNotificationId,
        notification,
    )
}