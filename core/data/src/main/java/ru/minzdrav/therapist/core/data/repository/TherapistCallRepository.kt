package ru.minzdrav.therapist.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.minzdrav.therapist.core.data.exceptions.UnauthorizedException
import ru.minzdrav.therapist.core.data.util.Syncable
import ru.minzdrav.therapist.core.model.TherapistCall

interface TherapistCallRepository : Syncable {
    @Throws(UnauthorizedException::class)
    fun getLatestTherapistCalls(): Flow<List<TherapistCall>>
}