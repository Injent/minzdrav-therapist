package ru.minzdrav.therapist.core.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import ru.minzdrav.therapist.core.data.repository.EncryptedDataRepository
import ru.minzdrav.therapist.core.data.util.NetworkMonitor
import ru.minzdrav.therapist.core.domain.model.AuthStatus
import javax.inject.Inject

class GetAuthStatusUseCase @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val encryptedDataRepository: EncryptedDataRepository
) {
    operator fun invoke(): Flow<AuthStatus> = combine(
        encryptedDataRepository.authorization,
        networkMonitor.isOnline
    ) { authorization, isOnline ->
        when {
            authorization == null -> AuthStatus.NOT_AUTHED
            isOnline -> AuthStatus.AUTHED
            else -> AuthStatus.AUTHED_OFFLINE
        }
    }
}