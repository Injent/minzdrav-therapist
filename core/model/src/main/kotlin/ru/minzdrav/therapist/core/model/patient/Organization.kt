package ru.minzdrav.therapist.core.model.patient

import kotlinx.serialization.Serializable

@Serializable
data class Organization(
    val identifier: String,
    val active: Boolean,
    val name: String,
    val alias: List<String>,
    val telecom: List<ContactPoint>,
    val address: List<Address>,
    val contact: Contact
) {
    @Serializable
    data class Contact(
        val purpose: String,
        val name: HumanName,
        val telecom: List<ContactPoint>,
        val address: Address
    )
}
