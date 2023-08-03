package ru.minzdrav.therapist.network

import ru.minzdrav.therapist.core.model.TherapistCall
import ru.minzdrav.therapist.core.model.forms.UiSchema

/**
 * Interface representing network calls to the Therapist backend
 */
interface TherapistNetworkDataSource {
    suspend fun getTherapistsCalls(): List<TherapistCall>
    suspend fun getUiSchema(id: String): UiSchema
}