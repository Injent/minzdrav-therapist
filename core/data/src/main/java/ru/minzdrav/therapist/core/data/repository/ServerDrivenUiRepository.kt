package ru.minzdrav.therapist.core.data.repository

import ru.minzdrav.therapist.core.model.forms.UiSchema

interface ServerDrivenUiRepository {
    suspend fun getUiSchema(id: String): UiSchema
}