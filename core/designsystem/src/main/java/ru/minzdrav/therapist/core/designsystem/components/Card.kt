package ru.minzdrav.therapist.core.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme
import ru.minzdrav.therapist.core.designsystem.theme.WhiteCard

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    elevation: Dp = AppTheme.dimen.cardElevation,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(AppTheme.dimen.cardPadding),
        shape = AppTheme.shapes.default,
        color = WhiteCard,
        shadowElevation = elevation,
        content = content,
    )
}