package ru.minzdrav.therapist.update.model

data class AppUpdateInfo internal constructor(
    val fileSize: Long,
    val versionCode: Int,
    val packageName: String,
    val installStatus: Int,
    val updateAvailability: Int,
    val url: String,
)