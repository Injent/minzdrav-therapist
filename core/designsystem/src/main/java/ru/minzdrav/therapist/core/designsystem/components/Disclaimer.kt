package ru.minzdrav.therapist.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme
import ru.minzdrav.therapist.core.designsystem.theme.BlackText
import ru.minzdrav.therapist.core.designsystem.theme.RedTheme

@Composable
fun ErrorDisclaimer(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimen.arrangementSpace),
        modifier = modifier
            .background(RedTheme.copy(.25f), AppTheme.shapes.small)
            .padding(AppTheme.dimen.screenPadding)
    ) {
        Text(
            text = title,
            style = AppTheme.typography.headline2,
            color = BlackText
        )

        Text(
            text = description,
            style = AppTheme.typography.body,
            color = BlackText
        )
    }
}