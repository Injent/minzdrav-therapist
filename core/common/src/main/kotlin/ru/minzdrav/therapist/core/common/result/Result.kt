package ru.minzdrav.therapist.core.common.result

sealed class Result<out A> {
    companion object {
        fun <T> success(t: T) = Success(t)
        fun failure(e: Throwable) = Failure(e)
        fun failure(message: String? = null) = Failure(IllegalStateException(message))
    }

    fun getOrNull(): A? = (this as? Success<A>)?.data

    fun isASuccess() = this is Success<*>

    fun isAFailure() = this is Failure

    data class Success<A>(val data: A) : Result<A>()
    data class Failure(val e: Throwable) : Result<Nothing>()
}