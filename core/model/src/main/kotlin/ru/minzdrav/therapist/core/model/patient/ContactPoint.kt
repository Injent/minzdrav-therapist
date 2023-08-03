package ru.minzdrav.therapist.core.model.patient

import kotlinx.serialization.Serializable

@Serializable
data class ContactPoint(
    val system: System,
    val value: String
) {
    enum class System {
        PHONE,
        FAX,
        EMAIL,
        PAGER,
        URL,
        SMS,
        OTHER
    }
}