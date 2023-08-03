package ru.minzdrav.therapist.core.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.minzdrav.therapist.core.designsystem.icon.AppIcons
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme
import ru.minzdrav.therapist.core.designsystem.theme.Gray1
import ru.minzdrav.therapist.core.designsystem.theme.GraySnackbar
import ru.minzdrav.therapist.core.designsystem.theme.WhiteText

data class AppSnackbarVisuals(
    override val duration: SnackbarDuration,
    override val message: String,
    override val withDismissAction: Boolean,
    @DrawableRes val closeIconResId: Int
) : SnackbarVisuals {
    override val actionLabel: String? = null
}

@Composable
fun AppSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(hostState = hostState) { data ->
        if (data.visuals is AppSnackbarVisuals) {
            val visuals = data.visuals as AppSnackbarVisuals

            AppSnackbar(
                text = visuals.message,
                iconResId = visuals.closeIconResId,
                onClose = data::dismiss,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun AppSnackbar(
    text: String,
    @DrawableRes iconResId: Int,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimen.arrangementSpace),
        modifier = modifier
            .fillMaxWidth()
            .height(AppSnackbarDefaults.height)
            .background(GraySnackbar, AppTheme.shapes.default)
            .padding(horizontal = AppSnackbarDefaults.contentPadding)
    ) {
        Icon(
            painter = painterResource(iconResId),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.CenterVertically)
        )

        Text(
            text = text,
            style = AppTheme.typography.footnote,
            color = WhiteText,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        )

        Icon(
            painter = painterResource(AppIcons.CloseSmall),
            contentDescription = null,
            tint = Gray1,
            modifier = Modifier
                .align(Alignment.Top)
                .padding(top = AppTheme.dimen.default)
                .size(20.dp)
                .clickable(onClick = onClose)
        )
    }
}

object AppSnackbarDefaults {
    val height = 52.dp
    val contentPadding = 16.dp
}