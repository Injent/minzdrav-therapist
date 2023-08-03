package ru.minzdrav.therapist.update.data.fake

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.minzdrav.therapist.update.data.FileDownloader
import java.io.File
import javax.inject.Inject

class FakeFileDownloader @Inject constructor(
    @ApplicationContext private val context: Context
) : FileDownloader {
    override suspend fun downloadFile(
        url: String,
        destination: File,
        listener: FileDownloader.Listener,
    ) {
        context.resources.assets.open("therapist_app.apk").use { input ->
            val bufferSize = 8 * 1024
            val buffer = ByteArray(bufferSize)
            var bytesRead: Int
            var bytesWritten = 0L
            val totalBytes = input.available().toLong()

            destination.outputStream().buffered(bufferSize).use { out ->
                while (input.read(buffer).also { bytesRead = it } != -1) {
                    out.write(buffer, 0, bytesRead)
                    bytesWritten += bytesRead

                    listener.onProgressChange(bytesWritten, totalBytes)
                }
            }
        }
    }
}