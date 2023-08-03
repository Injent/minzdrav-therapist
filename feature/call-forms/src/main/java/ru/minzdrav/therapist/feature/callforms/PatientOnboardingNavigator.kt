package ru.minzdrav.therapist.feature.callforms

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ramcosta.composedestinations.annotation.Destination
import ru.minzdrav.therapist.core.designsystem.components.AppButton
import ru.minzdrav.therapist.core.designsystem.components.AppTopBar
import ru.minzdrav.therapist.core.designsystem.components.AppTopBarAction
import ru.minzdrav.therapist.core.designsystem.components.BasicLayout
import ru.minzdrav.therapist.core.designsystem.icon.AppIcons
import ru.minzdrav.therapist.core.designsystem.theme.TherapistTheme

interface PatientOnboardingNavigator {
    fun navigateUp()
    fun navigateToCommonInspection()
}

data class PatientOnboardingNavArgs(
    val therapistCallId: Long
)

@Destination(navArgsDelegate = PatientOnboardingNavArgs::class)
@Composable
fun PatientOnboarding(
    navigator: PatientOnboardingNavigator
) {
    PatientOnboardingScreen(
        onBack = navigator::navigateUp,
        startForming = navigator::navigateToCommonInspection
    )
}

@Composable
private fun PatientOnboardingScreen(
    onBack: () -> Unit,
    startForming: () -> Unit
) {
    BasicLayout(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.title_information),
                navigationAction = {
                    AppTopBarAction(
                        iconResId = AppIcons.Back,
                        onClick = onBack
                    )
                },
            )
        },
        primaryButton = {
            AppButton(
                text = stringResource(R.string.action_start_filling_form),
                onClick = startForming,
                modifier = Modifier.fillMaxWidth()
            )
        },
    ) { padding ->
        LazyColumn(
            contentPadding = padding
        ) {

        }
    }
}


@Preview
@Composable
private fun PatientOnboardingScreenPreview() {
    TherapistTheme {

    }
}