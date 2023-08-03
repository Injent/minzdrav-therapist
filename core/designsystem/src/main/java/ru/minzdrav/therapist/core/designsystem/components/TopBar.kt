package ru.minzdrav.therapist.core.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme
import ru.minzdrav.therapist.core.designsystem.theme.Gray1
import ru.minzdrav.therapist.core.designsystem.theme.WhiteBackground

@Composable
fun AppTopBarAction(
    @DrawableRes iconResId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(iconResId),
            contentDescription = null,
            tint = Gray1,
            modifier = Modifier.size(AppTheme.dimen.iconSize)
        )
    }
}

@Composable
fun AppTopBarAction(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppTextButton(
        text = text,
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationAction: (@Composable () -> Unit)? = null,
    action: (@Composable () -> Unit)? = null,
    containerColor: Color = WhiteBackground
) {
    Row(
        modifier = modifier
            .background(containerColor)
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimen.screenPadding)
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        navigationAction?.let {
            navigationAction()
            Spacer(Modifier.width(AppTheme.dimen.smallSpace))
        }

        Text(
            text = title,
            style = AppTheme.typography.title3,
            maxLines = 1,
            modifier = Modifier.weight(1f)
        )

        action?.let {
            action()
        }
    }
}