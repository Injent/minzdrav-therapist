package ru.minzdrav.therapist.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme

@Composable
fun TextWithStatus(
    statusColor: Color,
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimen.arrangementSpace),
        modifier = modifier
    ) {
        Spacer(
            Modifier
                .align(Alignment.CenterVertically)
                .background(statusColor, CircleShape)
                .size(8.dp)
        )
        text()
    }
}