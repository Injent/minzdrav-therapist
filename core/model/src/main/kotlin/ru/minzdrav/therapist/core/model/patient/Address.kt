package ru.minzdrav.therapist.core.model.patient

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val territory: String,
    val populatedArea: String = territory,
    val street: String,
    val building: Int,
    val block: Int,
    val flatNumber: Int
) {
    fun asString(): String {
        return "$territory, $populatedArea, $street, $building/$block, $flatNumber"
    }
}