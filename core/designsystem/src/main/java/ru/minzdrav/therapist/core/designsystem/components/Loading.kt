package ru.minzdrav.therapist.core.designsystem.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import ru.minzdrav.therapist.core.designsystem.R

@Composable
fun AppCircularLoading(
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 720f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing =  FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart,
            initialStartOffset = StartOffset(offsetMillis = 700, StartOffsetType.FastForward)
        )
    )

    Icon(
        painter = painterResource(R.drawable.ic_loading),
        contentDescription = null,
        tint = tint,
        modifier = modifier.rotate(rotation)
    )
}