package ru.minzdrav.therapist.auth

import ru.minzdrav.therapist.auth.data.AuthRepository
import ru.minzdrav.therapist.auth.result.AuthResult
import ru.minzdrav.therapist.core.data.repository.EncryptedDataRepository
import javax.inject.Inject

class Authenticator @Inject constructor (
    private val authRepository: AuthRepository,
    private val encryptedDataRepository: EncryptedDataRepository
) {
    suspend fun signUp(login: String, password: String): AuthResult {
        return authRepository.signUp(login, password).also { result ->
            if (result is AuthResult.Authorized)
                encryptedDataRepository.setUserAuthorization(result.authorization)
        }
    }

    suspend fun signIn(login: String, password: String): AuthResult {
        return authRepository.signIn(login, password).also { result ->
            if (result is AuthResult.Authorized) {
                encryptedDataRepository.setUserAuthorization(result.authorization)
            }
        }
    }
}

