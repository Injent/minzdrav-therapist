package ru.minzdrav.therapist.core.model.patient

import kotlinx.serialization.Serializable

@Serializable
data class Polis(
    val type: Type,
    val series: Int? = null,
    val number: Long
) {
    enum class Type {
        OLD,
        NEW,
        TEMP
    }
}