package ru.minzdrav.therapist.update.data

import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.IOException
import okio.Source
import okio.buffer
import ru.minzdrav.therapist.core.common.AppDispatcher.IO
import ru.minzdrav.therapist.core.common.Dispatcher
import ru.minzdrav.therapist.update.errors.InstallErrorCode
import ru.minzdrav.therapist.update.errors.InstallException
import ru.minzdrav.therapist.update.util.availableSpace
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import kotlin.jvm.Throws

class PrivateStorageFileDownloader @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : FileDownloader {

    @Throws(InstallException::class)
    override suspend fun downloadFile(
        url: String,
        destination: File,
        listener: FileDownloader.Listener,
    ): Unit = with(ioDispatcher) {
        val request = Request.Builder()
            .url(url)
            .build()

        val client = OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                val origin = chain.proceed(chain.request())

                origin.body?.let { body ->
                    origin.newBuilder()
                        .body(ProgressResponseBody(body, listener))
                        .build()
                } ?: origin
            }.build()

        kotlin.runCatching {
            val response = client.newCall(request).execute()
            if (availableSpace() < (response.body?.contentLength() ?: 0)) {
                throw InstallException(InstallErrorCode.ERROR_STORAGE_LOW)
            }
            val out = FileOutputStream(destination)

            response.body?.let { body ->
                out.use {
                    it.write(body.bytes())
                }
            }
        }.onFailure { e ->
            if (e !is InstallException) {
                throw InstallException(InstallErrorCode.ERROR_DOWNLOAD)
            } else throw e
        }
    }

    private class ProgressResponseBody(
        val responseBody: ResponseBody,
        val listener: FileDownloader.Listener
    ) : ResponseBody() {
        lateinit var bufferedSource: BufferedSource

        override fun contentLength() = responseBody.contentLength()

        override fun contentType(): MediaType? = responseBody.contentType()

        override fun source(): BufferedSource {
            if (!::bufferedSource.isInitialized) {
                bufferedSource = source(responseBody.source()).buffer()
            }
            return bufferedSource
        }

        private fun source(source: Source): Source {
            return object : ForwardingSource(source) {
                var totalBytesRead = 0L
                override fun read(sink: Buffer, byteCount: Long): Long {
                    return super.read(sink, byteCount).also {
                        totalBytesRead += if (it != -1L) it else 0
                        listener.onProgressChange(responseBody.contentLength(), totalBytesRead)
                    }
                }
            }
        }
    }
}