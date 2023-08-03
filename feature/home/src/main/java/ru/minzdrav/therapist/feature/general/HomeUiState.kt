package ru.minzdrav.therapist.feature.general

import kotlinx.collections.immutable.ImmutableList
import ru.minzdrav.therapist.core.common.util.TextResource
import ru.minzdrav.therapist.core.model.TherapistCall

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Error(val errorTextRes: TextResource) : HomeUiState
    object Empty : HomeUiState
    data class Success(
        val calls: ImmutableList<TherapistCall>
    ) : HomeUiState
}