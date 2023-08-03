package ru.minzdrav.therapist.core.storage

import androidx.datastore.core.Serializer
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer : Serializer<UserPreferences> {
    override val defaultValue: UserPreferences = UserPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserPreferences = try {
        UserPreferences.parseFrom(input)
    } catch (e: IOException) {
        e.printStackTrace()
        defaultValue
    }

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) {
        t.writeTo(output)
    }
}