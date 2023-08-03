package ru.minzdrav.therapist.auth.util

import ru.minzdrav.therapist.auth.R
import ru.minzdrav.therapist.auth.result.ValidationResult
import ru.minzdrav.therapist.auth.result.ValidationResult.Failure
import ru.minzdrav.therapist.auth.result.ValidationResult.Success

object CredentialsValidator {
    private const val UPPERCASE_REGEX = "[\\p{Lu}\\p{Lm}]"
    private const val LOWERCASE_REGEX = "[\\p{Ll}\\p{Lm}]"
    private const val DIGIT_REGEX = "\\p{Nd}"
    private const val SPECIAL_CHARACTER_REGEX = "[^\\p{L}\\p{N}]"

    fun validateLogin(login: String): ValidationResult = if (login.isNotEmpty()) {
        Success
    } else {
        Failure(R.string.failure_login_is_empty)
    }

    fun validatePassword(password: String): ValidationResult = when {
        password.length < 8 -> {
            Failure(R.string.failure_password_is_short)
        }
        !password.contains(Regex(UPPERCASE_REGEX)) -> {
            Failure(R.string.failure_password_must_contain_uppercase)
        }
        !password.contains(Regex(LOWERCASE_REGEX)) -> {
            Failure(R.string.failure_password_must_contain_lowercase)
        }
        !password.contains(Regex(DIGIT_REGEX)) -> {
            Failure(R.string.failure_password_must_contain_digits)
        }
        !password.contains(Regex(SPECIAL_CHARACTER_REGEX)) -> {
            Failure(R.string.failure_password_must_contain_special_char)
        }
        else -> Success
    }
}