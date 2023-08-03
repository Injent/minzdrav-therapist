package ru.minzdrav.therapist.update.data.provider

import kotlinx.coroutines.flow.Flow
import ru.minzdrav.therapist.update.model.AppUpdateInfo

interface UpdateInfoProvider {
    val appUpdateInfo: Flow<AppUpdateInfo?>
}