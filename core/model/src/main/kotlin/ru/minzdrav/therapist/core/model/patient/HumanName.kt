package ru.minzdrav.therapist.core.model.patient

import kotlinx.serialization.Serializable

@Serializable
data class HumanName(
    val firstName: String,
    val lastName: String,
    val middleName: String? = null,
)