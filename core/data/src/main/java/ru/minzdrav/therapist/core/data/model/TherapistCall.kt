package ru.minzdrav.therapist.core.data.model

import ru.minzdrav.therapist.core.database.model.TherapistCallEntity
import ru.minzdrav.therapist.core.model.TherapistCall

fun TherapistCall.asEntity() = TherapistCallEntity(
    id, patient, complaints, registrationDate, status
)