package ru.minzdrav.therapist.core.common.util

import android.content.Context
import androidx.core.os.ConfigurationCompat
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import ru.minzdrav.therapist.core.common.di.Formatter
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDateFormatter @Inject constructor(
    @Formatter(NumericDateTime) private val numericDateTimeFormatter: DateTimeFormatter
) {
    fun formatNumericDateTime(instant: Instant): String {
        return numericDateTimeFormatter.format(instant.toJavaInstant())
    }

    companion object {
        const val NumericDateTime = "NumericDateTime"
    }
}

internal fun DateTimeFormatter.withLocale(context: Context): DateTimeFormatter {
    val locales = ConfigurationCompat.getLocales(context.resources.configuration)
    return when {
        locales.isEmpty -> this
        else -> this.withLocale(locales[0])
    }
}