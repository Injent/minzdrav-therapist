package ru.minzdrav.therapist.core.designsystem.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme
import ru.minzdrav.therapist.core.designsystem.theme.Blue
import ru.minzdrav.therapist.core.designsystem.theme.Blue2
import ru.minzdrav.therapist.core.designsystem.theme.LightBlue
import ru.minzdrav.therapist.core.designsystem.theme.WhiteText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppChip(
    label: String,
    onClick: () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(
                text = label,
                style = AppTheme.typography.calloutButton,
            )
        },
        shape = CircleShape,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = LightBlue,
            labelColor = Blue2,
            selectedContainerColor = Blue,
            selectedLabelColor = WhiteText
        ),
        border = null,
        modifier = modifier.height(AppChipTokens.Height)
    )
}

internal object AppChipTokens {
    val Height: Dp = 36.dp
}