package ru.minzdrav.therapist.core.model.forms

import kotlinx.serialization.Serializable

@Serializable
data class UiSchema(
    val version: Int,
    val fields: List<ViewKind>
)
