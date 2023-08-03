package ru.minzdrav.therapist.core.designsystem.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme
import ru.minzdrav.therapist.core.designsystem.theme.Blue
import ru.minzdrav.therapist.core.designsystem.theme.BlueCobalt
import ru.minzdrav.therapist.core.designsystem.theme.LightBlue
import ru.minzdrav.therapist.core.designsystem.theme.Gray2
import ru.minzdrav.therapist.core.designsystem.theme.GrayBody
import ru.minzdrav.therapist.core.designsystem.theme.GrayDisabled
import ru.minzdrav.therapist.core.designsystem.theme.TherapistTheme
import ru.minzdrav.therapist.core.designsystem.theme.WhiteBackground
import ru.minzdrav.therapist.core.designsystem.theme.WhiteText

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = AppTheme.shapes.default,
    isLoading: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(horizontal = 20.dp)
) {
    BaseButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minWidth = 240.dp),
        enabled = enabled,
        shape = shape,
        isLoading = isLoading,
        contentPadding = contentPadding
    ) {
        AppTextWithLoading(
            text = text,
            isLoading = isLoading,
            loadingSize = 26.dp
        )
    }
}

@Composable
fun AppOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = AppTheme.shapes.default,
    isLoading: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val borderColor by animateColorAsState(
        targetValue = if (isPressed) BlueCobalt else if (!enabled) Gray2 else Blue,
        animationSpec = tween(
            durationMillis = 100,
            easing = LinearEasing
        )
    )

    val containerColor by animateColorAsState(
        targetValue = if (isPressed) GrayBody else WhiteBackground,
        animationSpec = tween(
            durationMillis = 100,
            easing = LinearEasing
        )
    )

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        OutlinedButton(
            onClick = {
                if (!isLoading) {
                    onClick()
                }
            },
            enabled = enabled,
            shape = shape,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = containerColor,
                contentColor = borderColor,
                disabledContainerColor = WhiteBackground,
                disabledContentColor = Gray2
            ),
            border = BorderStroke(1.dp, borderColor),
            contentPadding = PaddingValues(horizontal = 20.dp),
            interactionSource = interactionSource,
            modifier = modifier
                .requiredHeight(60.dp)
                .defaultMinSize(240.dp),
        ) {
            AppTextWithLoading(
                text = text,
                isLoading = isLoading,
                loadingSize = 26.dp
            )
        }
    }
}

@Composable
fun AppTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 4.dp),
        shape = AppTheme.shapes.small,
        colors = ButtonDefaults.textButtonColors(
            contentColor = Blue,
            disabledContentColor = GrayDisabled
        ),
    ) {
        Text(
            text = text,
            style = AppTheme.typography.subheadlineButton
        )
    }
}

@Composable
private fun AppTextWithLoading(
    text: String,
    textStyle: TextStyle = AppTheme.typography.bodyButton,
    loadingSize: Dp,
    isLoading: Boolean = false
) {
    Box {
        val alphaTransitionText by animateFloatAsState(
            targetValue = if (isLoading) 0f else 1f,
            animationSpec = tween()
        )
        val scaleTransitionText by animateFloatAsState(
            targetValue = if (isLoading) 0.75f else 1f
        )
        val alphaTransitionLoading by animateFloatAsState(
            targetValue = if (isLoading) 1f else 0f
        )

        Text(
            text = text,
            style = textStyle,
            modifier = Modifier
                .alpha(alphaTransitionText)
                .scale(scaleTransitionText)
                .align(Alignment.Center)
        )

        if (isLoading)
            AppCircularLoading(
                modifier = Modifier
                    .size(loadingSize)
                    .alpha(alphaTransitionLoading)
                    .align(Alignment.Center)
            )
    }
}

@Composable
private fun BaseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = AppTheme.shapes.default,
    isLoading: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(horizontal = 20.dp),
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val color by animateColorAsState(
        targetValue = if (isPressed) BlueCobalt else Blue,
        animationSpec = tween(
            durationMillis = 100,
            easing = LinearEasing
        )
    )

    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
        Button(
            onClick = {
                if (!isLoading) {
                    onClick()
                }
            },
            enabled = enabled,
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                containerColor = color,
                contentColor = WhiteText,
                disabledContainerColor = GrayDisabled,
                disabledContentColor = LightBlue
            ),
            contentPadding = contentPadding,
            interactionSource = interactionSource,
            modifier = modifier
                .requiredHeight(60.dp),
        ) {
            content()
        }
    }
}

@Composable
fun AppSmallButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = CircleShape,
    isLoading: Boolean = false,
) {
    BaseButton(
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        isLoading = isLoading,
        contentPadding = PaddingValues(horizontal = 12.dp),
        modifier = modifier.requiredHeight(36.dp),
    ) {
        AppTextWithLoading(
            text = text,
            loadingSize = 18.dp,
            textStyle = AppTheme.typography.calloutButton,
            isLoading = isLoading
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF,
    widthDp = 344
)
@Composable
private fun DefaultAppButtonPreview() {
    TherapistTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(AppTheme.dimen.default)
        ) {
            var isLoading by remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()

            AppButton(
                text = "Text button",
                onClick = {
                    isLoading = true
                    scope.launch {
                        delay(5000)
                        isLoading = false
                    }
                },
                isLoading = isLoading
            )

            AppOutlinedButton(
                text = "Text button",
                onClick = {
                    isLoading = true
                    scope.launch {
                        delay(5000)
                        isLoading = false
                    }
                },
                isLoading = isLoading
            )

            AppSmallButton(
                text = "Text button",
                onClick = {
                    isLoading = true
                    scope.launch {
                        delay(5000)
                        isLoading = false
                    }
                },
                isLoading = isLoading
            )
        }
    }
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f,0.0f,0.0f,0.0f)
}