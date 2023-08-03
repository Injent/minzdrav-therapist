package ru.minzdrav.therapist.auth.result

import androidx.annotation.StringRes

sealed interface ValidationResult {
    data class Failure(@StringRes val messageTextId: Int) : ValidationResult
    object Success : ValidationResult
}