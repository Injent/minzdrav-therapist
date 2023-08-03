package ru.minzdrav.therapist.core.storage.encrypted

import androidx.datastore.core.Serializer
import com.google.crypto.tink.Aead
import ru.minzdrav.therapist.core.storage.EncryptedData
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class EncryptedDataSerializer(private val aead: Aead) : Serializer<EncryptedData> {
    override val defaultValue: EncryptedData = EncryptedData.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): EncryptedData = try {
        val encryptedInput = input.readBytes()

        val decryptedInput = if (encryptedInput.isNotEmpty()) {
            aead.decrypt(encryptedInput, null)
        } else {
            encryptedInput
        }

        EncryptedData.parseFrom(decryptedInput)
    } catch (e: IOException) {
        e.printStackTrace()
        defaultValue
    }

    override suspend fun writeTo(t: EncryptedData, output: OutputStream) {
        val bytes = t.toByteArray()
        val encryptedBytes = aead.encrypt(bytes, null)

        output.write(encryptedBytes)
    }
}