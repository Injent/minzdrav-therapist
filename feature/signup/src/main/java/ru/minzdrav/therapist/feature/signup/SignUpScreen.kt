package ru.minzdrav.therapist.feature.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination

interface SignUpNavigator {
    fun navigateUp()
}

@Destination
@Composable
fun SignUp(
    navigator: SignUpNavigator
) {
    val viewModel: SignUpViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SignUpScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun SignUpScreen(
    uiState: SignUpUiState,
    onEvent: (SignUpEvent) -> Unit
) {

}