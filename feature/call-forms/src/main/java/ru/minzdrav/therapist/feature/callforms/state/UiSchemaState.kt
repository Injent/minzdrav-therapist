package ru.minzdrav.therapist.feature.callforms.state

import androidx.compose.runtime.Stable
import ru.minzdrav.therapist.core.common.util.TextResource
import ru.minzdrav.therapist.core.model.forms.UiSchema

@Stable
sealed interface UiSchemaState {
    data object Loading : UiSchemaState
    data class Success(val schemas: Map<String, UiSchema>) : UiSchemaState
    data class Error(val errorText: TextResource) : UiSchemaState
}