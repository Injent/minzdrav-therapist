package ru.minzdrav.therapist.core.ui.model

import androidx.compose.runtime.Immutable
import kotlinx.datetime.Instant
import ru.minzdrav.therapist.core.model.CallStatus
import ru.minzdrav.therapist.core.model.TherapistCall

@Immutable
data class UiTherapistCall(
    val id: Long,
    val fullName: String,
    val age: Int,
    val status: CallStatus,
    val address: String,
    val registrationDate: Instant,
)

fun TherapistCall.asUiModel() = UiTherapistCall(
    id = id,
    fullName = with(patient.name) { "$lastName $firstName" },
    age = 0,
    status = status,
    address = patient.currentAddress.asString(),
    registrationDate = registrationDate
)