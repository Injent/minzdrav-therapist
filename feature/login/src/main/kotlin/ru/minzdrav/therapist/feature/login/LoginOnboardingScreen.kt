package ru.minzdrav.therapist.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ramcosta.composedestinations.annotation.Destination
import ru.minzdrav.therapist.core.designsystem.components.AppButton
import ru.minzdrav.therapist.core.designsystem.components.AppTextButton
import ru.minzdrav.therapist.core.designsystem.illustrations.AppIllustrations
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme
import ru.minzdrav.therapist.core.designsystem.theme.GrayDark
import ru.minzdrav.therapist.core.designsystem.theme.TherapistTheme
import ru.minzdrav.therapist.feature.login.navigation.LoginNavigator

@Destination
@Composable
fun LoginOnboarding(
    navigator: LoginNavigator
) {
    LoginOnboardingScreen(
        onSignIn = navigator::navigateToSignIn,
        onSignUp = {},
        onPasswordRecover = {}
    )
}

@Composable
private fun LoginOnboardingScreen(
    onSignIn: () -> Unit,
    onSignUp: () -> Unit,
    onPasswordRecover: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimen.arrangementSpace),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(AppTheme.dimen.screenPadding),
    ) {
        Box(Modifier.weight(1f)) {
            Image(
                painter = painterResource(AppIllustrations.AppLogo),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(.65f)
            )
        }

        AppButton(
            text = stringResource(R.string.action_sign_in),
            onClick = onSignIn,
            modifier = Modifier
                .fillMaxWidth(),
        )

        AppTextButton(
            text = stringResource(R.string.action_recover_password),
            onClick = onPasswordRecover,
        )

        Spacer(Modifier.height(AppTheme.dimen.mediumSpace))

        Text(
            text = stringResource(R.string.tip_first_time),
            color = GrayDark,
            style = AppTheme.typography.subheadline,
        )

        AppTextButton(
            text = stringResource(R.string.action_sign_up),
            onClick = onSignUp,
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun LoginOnboardingPreview() {
    TherapistTheme {
        LoginOnboardingScreen(
            onSignIn = {},
            onSignUp = {},
            onPasswordRecover = {}
        )
    }
}