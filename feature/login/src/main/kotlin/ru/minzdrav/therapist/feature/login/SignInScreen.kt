package ru.minzdrav.therapist.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import ru.minzdrav.therapist.core.designsystem.components.AppButton
import ru.minzdrav.therapist.core.designsystem.components.AppTextButton
import ru.minzdrav.therapist.core.designsystem.components.AppTextField
import ru.minzdrav.therapist.core.designsystem.components.AppTopBar
import ru.minzdrav.therapist.core.designsystem.components.AppTopBarAction
import ru.minzdrav.therapist.core.designsystem.components.BasicLayout
import ru.minzdrav.therapist.core.designsystem.components.ErrorDisclaimer
import ru.minzdrav.therapist.core.designsystem.icon.AppIcons
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme
import ru.minzdrav.therapist.core.designsystem.theme.TherapistTheme
import ru.minzdrav.therapist.feature.login.navigation.LoginNavigator

@Destination
@Composable
fun SignInScreen(
    navigator: LoginNavigator
) {
    val viewModel: SignInViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isAuthed) {
        if (uiState.isAuthed)
            navigator.navigateToGeneral()
    }
    SignInScreen(
        onEvent = viewModel::onEvent,
        uiState = uiState,
        onNavigateBack = navigator::navigateUp,
    )
}

@Composable
private fun SignInScreen(
    onEvent: (SignInEvent) -> Unit,
    uiState: SignInUiState,
    onNavigateBack: () -> Unit
) {
    BasicLayout(
        topBar = {
            SignInTopBar(onNavigateBack = onNavigateBack)
        },
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                AppTheme.dimen.mediumSpace, Alignment.CenterVertically
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = AppTheme.dimen.cardPadding)
        ) {
            CredentialsFields(
                uiState = uiState,
                onEvent = onEvent
            )

            AppButton(
                text = stringResource(R.string.action_sign_in),
                onClick = { onEvent(SignInEvent.SignIn) },
                enabled = uiState.credentialsIsValid,
                modifier = Modifier.fillMaxWidth(),
                isLoading = uiState.isLoading
            )

            AppTextButton(
                text = stringResource(R.string.action_cant_log_in),
                onClick = { onEvent(SignInEvent.CantLogin) },
            )
        }
    }
}

@Composable
private fun ColumnScope.CredentialsFields(
    uiState: SignInUiState,
    onEvent: (SignInEvent) -> Unit,
) {
    val widthModifier = Modifier.fillMaxWidth()

    uiState.error?.let { error ->
        ErrorDisclaimer(
            title = stringResource(error.titleTextId),
            description = stringResource(error.descriptionTextId),
            modifier = widthModifier
        )
        Spacer(Modifier.height(AppTheme.dimen.smallSpace))
    }

    AppTextField(
        text = uiState.login.value,
        onValueChange = { onEvent(SignInEvent.ChangeLogin(it)) },
        label = stringResource(R.string.label_login),
        modifier = widthModifier,
        outlined = true,
        error = uiState.login.errorTextId?.let { stringResource(it) },
    )

    AppTextField(
        text = uiState.password.value,
        onValueChange = { onEvent(SignInEvent.ChangePassword(it)) },
        label = stringResource(R.string.label_password),
        modifier = widthModifier,
        outlined = true,
        error = uiState.password.errorTextId?.let { stringResource(it) },
    )
}

@Composable
private fun SignInTopBar(onNavigateBack: () -> Unit) {
    AppTopBar(
        title = stringResource(R.string.label_login),
        navigationAction = {
            AppTopBarAction(
                iconResId = AppIcons.Back,
                onClick = onNavigateBack
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun SignInScreenPreview() {
    TherapistTheme {
        SignInScreen(
            onEvent = {},
            uiState = SignInUiState(),
            onNavigateBack = {}
        )
    }
}