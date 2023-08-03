package ru.minzdrav.therapist.update.model

import androidx.work.Data
import androidx.work.workDataOf
import ru.minzdrav.therapist.update.errors.InstallErrorCode

data class InstallState(
    val installStatus: Int,
    val bytesDownloaded: Long,
    val totalBytesToDownload: Long,
    val errorCode: Int = InstallErrorCode.ERROR_NONE
) {
    constructor(workData: Data) : this(
        installStatus = workData.getInt(KEY_INSTALL_STATUS, InstallStatus.UNKNOWN),
        bytesDownloaded = workData.getLong(KEY_BYTES_DOWNLOADED, 0),
        totalBytesToDownload = workData.getLong(KEY_TOTAL_BYTES_TO_DOWNLOAD, 0),
        errorCode = workData.getInt(KEY_ERROR, InstallErrorCode.ERROR_NONE)
    )

    fun asWorkData() = workDataOf(
        KEY_INSTALL_STATUS to installStatus,
        KEY_BYTES_DOWNLOADED to bytesDownloaded,
        KEY_TOTAL_BYTES_TO_DOWNLOAD to totalBytesToDownload,
        KEY_ERROR to errorCode
    )

    companion object {
        const val KEY_INSTALL_STATUS = "install_status"
        const val KEY_BYTES_DOWNLOADED = "bytes_downloaded"
        const val KEY_TOTAL_BYTES_TO_DOWNLOAD = "total_bytes_to_download"
        const val KEY_ERROR = "error"

        fun defaultInstance() = InstallState(
            InstallStatus.UNKNOWN,
            bytesDownloaded = 0,
            totalBytesToDownload = 0
        )
    }
}