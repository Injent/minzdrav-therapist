package ru.minzdrav.therapist.update.data.fake

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build.VERSION.SDK_INT
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.minzdrav.therapist.core.common.AppDispatcher.IO
import ru.minzdrav.therapist.core.common.Dispatcher
import ru.minzdrav.therapist.update.data.provider.UpdateInfoProvider
import ru.minzdrav.therapist.update.model.AppUpdateInfo
import ru.minzdrav.therapist.update.model.InstallStatus
import ru.minzdrav.therapist.update.model.UpdateAvailability
import javax.inject.Inject

class FakeUpdateInfoProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : UpdateInfoProvider {
    override val appUpdateInfo: Flow<AppUpdateInfo?> = flow {
        emit(null)

        @Suppress("BlockingMethodInNonBlockingContext")
        val size = context.resources.assets.open("therapist_app.apk").available()
        val versionCode = if (SDK_INT >= 33) {
            context.packageManager.getPackageInfo(
                context.packageName, PackageManager.PackageInfoFlags.of(0)
            ).longVersionCode.toInt()
        } else {
            @Suppress("DEPRECATION")
            context.packageManager.getPackageInfo(context.packageName, 0).versionCode
        } + 1 // emitation of update

        emit(
            AppUpdateInfo(
                fileSize = size.toLong(),
                versionCode = versionCode,
                packageName = context.packageName,
                installStatus = InstallStatus.DOWNLOADED,
                updateAvailability = UpdateAvailability.UPDATE_AVAILABLE,
                url = ""
            )
        )
    }.flowOn(ioDispatcher)
}