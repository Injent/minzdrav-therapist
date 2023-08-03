package ru.minzdrav.therapist.feature.update

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.minzdrav.therapist.update.manager.AppUpdateManager
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val appUpdateManager: AppUpdateManager
) : ViewModel() {
    fun startInstallation() {
        appUpdateManager.installAppUpdate()
    }

    fun down() {
        appUpdateManager.downloadAppUpdate()
    }
}