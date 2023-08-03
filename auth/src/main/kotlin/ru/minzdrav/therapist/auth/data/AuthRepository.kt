package ru.minzdrav.therapist.auth.data

import ru.minzdrav.therapist.auth.result.AuthResult

interface AuthRepository {
    /**
     * Creates a user account from the request data, if account does not exist
     */
    suspend fun signUp(email: String, password: String): AuthResult

    /**
     * Logs in to the user account, if it exists, and receives an authorization token
     */
    suspend fun signIn(email: String, password: String): AuthResult
}