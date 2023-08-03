package ru.minzdrav.therapist.update.data.provider

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.minzdrav.therapist.update.model.AppUpdateInfo
import javax.inject.Inject

class NetworkUpdateInfoProvider @Inject constructor() : UpdateInfoProvider {
    override val appUpdateInfo: Flow<AppUpdateInfo?> = flow {

    }
}