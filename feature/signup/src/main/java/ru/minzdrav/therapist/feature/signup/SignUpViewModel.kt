package ru.minzdrav.therapist.feature.signup

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class SignUpViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState>
        get() = _uiState.asStateFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.ChangeFirstName -> TODO()
            is SignUpEvent.ChangeLastName -> TODO()
            is SignUpEvent.ChangeLogin -> TODO()
            is SignUpEvent.ChangePassword -> TODO()
            SignUpEvent.SignUp -> TODO()
        }
    }
}

@Immutable
internal data class SignUpUiState(
    val firstName: String = "",
    val lastName: String = "",
    val login: String = "",
    val password: String = "",
    @StringRes val errorTextId: Int? = null
)