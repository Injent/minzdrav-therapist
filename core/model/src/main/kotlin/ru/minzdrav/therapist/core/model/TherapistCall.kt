package ru.minzdrav.therapist.core.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import ru.minzdrav.therapist.core.model.patient.Patient

@Serializable
data class TherapistCall(
    val id: Long,
    val patient: Patient,
    val complaints: String,
    val status: CallStatus,
    val registrationDate: Instant
)
