package ru.minzdrav.therapist.feature.login

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.minzdrav.therapist.auth.Authenticator
import ru.minzdrav.therapist.auth.result.AuthResult
import ru.minzdrav.therapist.auth.result.ValidationResult
import ru.minzdrav.therapist.auth.util.CredentialsValidator
import ru.minzdrav.therapist.core.data.util.SyncManager
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticator: Authenticator,
    private val syncManager: SyncManager
) : ViewModel() {
    private val _uiState: MutableStateFlow<SignInUiState> = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState>
        get() = _uiState.asStateFlow()

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.ChangeLogin -> changeLogin(event.value.trim())
            is SignInEvent.ChangePassword -> changePassword(event.value.trim())
            SignInEvent.SignIn -> signIn(_uiState.value.login.value, _uiState.value.password.value)
            SignInEvent.CantLogin -> TODO()
        }
    }

    private fun changeLogin(value: String) = _uiState.update {
        val field = when (val result = CredentialsValidator.validateLogin(value)) {
            is ValidationResult.Failure -> SignInField(value, result.messageTextId)
            ValidationResult.Success -> SignInField(value)
        }

        it.copy(
            login = field,
            credentialsIsValid = field.errorTextId == null
                    && _uiState.value.login.errorTextId == null
        )
    }

    private fun changePassword(value: String) = _uiState.update {
        val field = when (val result = CredentialsValidator.validatePassword(value)) {
            is ValidationResult.Failure -> SignInField(value, result.messageTextId)
            ValidationResult.Success -> SignInField(value)
        }

        it.copy(
            password = field,
            credentialsIsValid = field.errorTextId == null
                    && _uiState.value.login.errorTextId == null
        )
    }

    private fun signIn(login: String, password: String) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            when (authenticator.signIn(login, password)) {
                is AuthResult.Authorized -> _uiState.update {
                    syncManager.setSyncRequired(true)
                    syncManager.requestSync()
                    it.copy(error = null, isAuthed = true)
                }
                AuthResult.NetworkConnectionError -> _uiState.update {
                    it.copy(
                        error = SignInError(
                            titleTextId = R.string.error_network_connection,
                            descriptionTextId = R.string.error_network_connection_description
                        )
                    )
                }
                is AuthResult.ServerError -> _uiState.update {
                    it.copy(
                        error = SignInError(
                            titleTextId = R.string.error_server,
                            descriptionTextId = R.string.error_server_auth_description
                        )
                    )
                }
                AuthResult.Unauthorized -> _uiState.update {
                    it.copy(
                        error = SignInError(
                            titleTextId = R.string.error_wrong_credentials,
                            descriptionTextId = R.string.error_wrong_credentials_description
                        )
                    )
                }
                is AuthResult.UnknownError -> _uiState.update {
                    it.copy(
                        error = SignInError(
                            titleTextId = R.string.error_unknown,
                            descriptionTextId = R.string.error_server_auth_description
                        )
                    )
                }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}

@Immutable
data class SignInUiState(
    val login: SignInField = SignInField("user"),
    val password: SignInField = SignInField("sam1\$Aeee"),
    val credentialsIsValid: Boolean = false,
    val error: SignInError? = null,
    val isLoading: Boolean = false,
    val isAuthed: Boolean = false
)

@Immutable
data class SignInField(
    val value: String = "",
    @StringRes val errorTextId: Int? = null
)

@Immutable
data class SignInError(
    @StringRes val titleTextId: Int,
    @StringRes val descriptionTextId: Int
)