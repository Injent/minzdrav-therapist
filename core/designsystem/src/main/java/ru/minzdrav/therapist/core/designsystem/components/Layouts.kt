package ru.minzdrav.therapist.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme
import ru.minzdrav.therapist.core.designsystem.theme.WhiteBackground
import ru.minzdrav.therapist.core.designsystem.theme.WhiteOpacity2

@Composable
fun BasicLayout(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit,
    bottomBar: (@Composable () -> Unit)? = null,
    primaryButton: (@Composable () -> Unit)? = null,
    containerColor: Color = WhiteBackground,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        containerColor = containerColor,
        modifier = modifier,
        topBar = topBar,
        bottomBar = {
            primaryButton?.let {
                Column(
                    verticalArrangement = Arrangement.spacedBy(AppTheme.dimen.arrangementSpace),
                    modifier = Modifier
                        .background(WhiteOpacity2)
                        .padding(AppTheme.dimen.screenPadding)
                ) {
                    primaryButton()
                }
            }
            bottomBar?.invoke()
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}