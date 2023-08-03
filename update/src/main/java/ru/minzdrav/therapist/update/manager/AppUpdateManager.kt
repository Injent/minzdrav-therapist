package ru.minzdrav.therapist.update.manager

import kotlinx.coroutines.flow.Flow
import ru.minzdrav.therapist.update.model.AppUpdateInfo
import ru.minzdrav.therapist.update.model.InstallState

interface AppUpdateManager {
    val updateInfo: Flow<AppUpdateInfo?>
    val installState: Flow<InstallState>
    fun downloadAppUpdate()
    fun installAppUpdate()
}