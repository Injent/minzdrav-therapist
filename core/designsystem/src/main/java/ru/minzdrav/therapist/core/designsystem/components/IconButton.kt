package ru.minzdrav.therapist.core.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.minzdrav.therapist.core.designsystem.R
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme
import ru.minzdrav.therapist.core.designsystem.theme.Blue
import ru.minzdrav.therapist.core.designsystem.theme.LightBlue
import ru.minzdrav.therapist.core.designsystem.theme.Gray2
import ru.minzdrav.therapist.core.designsystem.theme.GrayFooter
import ru.minzdrav.therapist.core.designsystem.theme.TherapistTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppFilledIconButton(
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    contentDescription: String? = null,
    shape: Shape = AppTheme.shapes.default
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        FilledIconButton(
            onClick = onClick,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = LightBlue,
                contentColor = Blue,
                disabledContainerColor = GrayFooter,
                disabledContentColor = Gray2
            ),
            shape = shape,
            modifier = modifier.size(40.dp)
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = contentDescription,
                tint = tint,
                modifier = Modifier.sizeIn(
                    minWidth = 12.dp, minHeight = 12.dp,
                    maxWidth = 20.dp, maxHeight = 20.dp
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppIconButton(
    onClick: () -> Unit,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    contentDescription: String? = null,
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        IconButton(
            onClick = onClick,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = tint,
                disabledContentColor = Gray2
            ),
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = contentDescription,
                modifier = modifier.sizeIn(
                    minWidth = 12.dp, minHeight = 12.dp,
                    maxWidth = 20.dp, maxHeight = 20.dp
                )
            )
        }
    }
}

@Preview
@Composable
private fun DefaultAppFilledIconButton() {
    TherapistTheme {
        AppFilledIconButton(onClick = {}, icon = R.drawable.ic_calendar)
    }
}

@Preview
@Composable
private fun DefaultAppIconButton() {
    TherapistTheme {
        AppIconButton(onClick = {}, icon = R.drawable.ic_calendar)
    }
}