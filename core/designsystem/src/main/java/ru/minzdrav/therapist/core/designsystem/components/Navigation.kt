package ru.minzdrav.therapist.core.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme
import ru.minzdrav.therapist.core.designsystem.theme.Blue
import ru.minzdrav.therapist.core.designsystem.theme.Gray1
import ru.minzdrav.therapist.core.designsystem.theme.GrayDark
import ru.minzdrav.therapist.core.designsystem.theme.WhiteCard

@Composable
fun RowScope.AppNavigationItem(
    onClick: () -> Unit,
    @DrawableRes iconResId: Int,
    label: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false
) {
    BaseNavItem(
        modifier = modifier.weight(1f),
        onClick = onClick,
        iconResId = iconResId,
        label = label,
        selected = selected
    )
}

@Composable
fun ColumnScope.AppNavigationItem(
    onClick: () -> Unit,
    @DrawableRes iconResId: Int,
    label: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false
) {
    BaseNavItem(
        modifier = modifier,
        onClick = onClick,
        iconResId = iconResId,
        label = label,
        selected = selected
    )
}

@Composable
private fun BaseNavItem(
    modifier: Modifier,
    onClick: () -> Unit,
    @DrawableRes iconResId: Int,
    label: String,
    selected: Boolean = false
) {
    val iconColor by animateColorAsState(
        targetValue = if (selected) Blue else Gray1
    )

    val textColor by animateColorAsState(
        targetValue = if (selected) Blue else GrayDark
    )

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = interactionSource
            )
    ) {
        Icon(
            painter = painterResource(iconResId),
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier
                .size(AppTheme.dimen.smallIconSize)
        )
        Text(
            text = label,
            style = AppTheme.typography.footnote,
            color = textColor
        )
    }
}

@Composable
fun AppBottomNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(BottomNavigationBarDefaults.Height)
            .background(WhiteCard)
    ) {
        content()
    }
}

@Composable
fun AppNavigationRail(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimen.mediumSpace),
        modifier = modifier
            .width(NavigationRailDefaults.Width)
            .background(WhiteCard)
            .padding(contentPadding)
    ) {
        content()
    }
}

object BottomNavigationBarDefaults {
    val Height: Dp = 56.dp
}

object NavigationRailDefaults {
    val Width: Dp = 72.dp
}