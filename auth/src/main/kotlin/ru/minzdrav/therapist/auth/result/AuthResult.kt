package ru.minzdrav.therapist.auth.result

import ru.minzdrav.therapist.core.model.UserAuthorization

sealed interface AuthResult {
    data class Authorized(val authorization: UserAuthorization) : AuthResult
    object Unauthorized : AuthResult
    class ServerError(val e: Throwable? = null) : AuthResult
    object NetworkConnectionError : AuthResult
    data class UnknownError(val e: Throwable? = null) : AuthResult
}