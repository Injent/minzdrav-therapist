package ru.minzdrav.therapist.core.designsystem.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.minzdrav.therapist.core.designsystem.theme.WhiteBackground

@Composable
fun AppBackground(
    modifier: Modifier = Modifier,
    color: Color = WhiteBackground,
    content: @Composable () -> Unit
) {
    Surface(
        color = color,
        modifier = modifier.fillMaxSize(),
    ) {
        content()
    }
}