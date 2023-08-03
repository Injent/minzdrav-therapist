package ru.minzdrav.therapist.core.model.patient

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Patient(
    val name: HumanName,
    val birthDate: LocalDate,
    val sex: Sex,
    val residenceAddress: Address,
    val currentAddress: Address,
    val contactNumber: String,
    val polis: Polis,
    val managingOrganization: Organization
) {
    enum class Sex {
        MALE,
        FEMALE,
        UNKNOWN
    }
}
