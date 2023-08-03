package ru.minzdrav.therapist.core.domain

import kotlinx.coroutines.flow.Flow
import ru.minzdrav.therapist.core.data.exceptions.UnauthorizedException
import ru.minzdrav.therapist.core.data.repository.TherapistCallRepository
import ru.minzdrav.therapist.core.model.TherapistCall
import javax.inject.Inject

class GetTherapistCallsUseCase @Inject constructor(
    private val therapistCallRepository: TherapistCallRepository
) {
    @Throws(UnauthorizedException::class)
    operator fun invoke(): Flow<List<TherapistCall>> = therapistCallRepository.getLatestTherapistCalls()
}