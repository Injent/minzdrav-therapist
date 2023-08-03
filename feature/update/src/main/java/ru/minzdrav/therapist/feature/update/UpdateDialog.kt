package ru.minzdrav.therapist.feature.update

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet
import ru.minzdrav.therapist.core.designsystem.components.AppButton
import ru.minzdrav.therapist.core.designsystem.illustrations.AppIllustrations
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme
import ru.minzdrav.therapist.core.designsystem.theme.BlackText
import ru.minzdrav.therapist.core.designsystem.theme.GrayDark
import ru.minzdrav.therapist.core.designsystem.theme.TherapistTheme
import ru.minzdrav.therapist.core.designsystem.theme.WhiteCard

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun UpdateBottomSheet() {
    val viewModel: UpdateViewModel = hiltViewModel()

    val perm = rememberPermissionState(Manifest.permission.INSTALL_PACKAGES) {
        println(it)
        viewModel.startInstallation()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteCard, AppTheme.shapes.defaultTopCarved)
            .padding(top = AppTheme.dimen.cardPadding)
    ) {
        AppButton(text = "A", onClick = viewModel::down)
        UpdateDialogContent(
            onUpdateClick = {
                perm.launchPermissionRequest()
            }
        )
    }
}

@Composable
private fun ColumnScope.UpdateDialogContent(onUpdateClick: () -> Unit) {
    Image(
        painter = painterResource(AppIllustrations.AppUpdateAvailable),
        contentDescription = null,
        modifier = Modifier.align(Alignment.CenterHorizontally)
    )

    Spacer(Modifier.height(28.dp))

    Text(
        text = stringResource(R.string.update_available),
        style = AppTheme.typography.title3,
        color = BlackText,
        modifier = Modifier.align(Alignment.CenterHorizontally)
    )

    Spacer(Modifier.height(12.dp))

    Text(
        text = stringResource(R.string.hint_update_available),
        style = AppTheme.typography.callout,
        color = GrayDark,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(AppTheme.dimen.cardPadding)
    )

    Spacer(Modifier.height(AppTheme.dimen.largeSpace))

    AppButton(
        text = stringResource(R.string.install),
        onClick = onUpdateClick,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun UpdateBottomSheetPreview() {
    TherapistTheme {
        Column(Modifier.padding(AppTheme.dimen.screenSafePadding)) {
            UpdateDialogContent(onUpdateClick = {})
        }
    }
}