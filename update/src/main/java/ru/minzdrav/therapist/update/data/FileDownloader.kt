package ru.minzdrav.therapist.update.data

import ru.minzdrav.therapist.update.errors.InstallException
import java.io.File

interface FileDownloader {
    @Throws(InstallException::class)
    suspend fun downloadFile(url: String, destination: File, listener: Listener)

    fun interface Listener {
        fun onProgressChange(bytesDownloaded: Long, totalBytesToDownload: Long)
    }
}