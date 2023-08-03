package ru.minzdrav.therapist.core.designsystem.theme

import android.app.Activity
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.core.view.WindowCompat
import ru.minzdrav.therapist.core.common.util.AppDateFormatter

@Immutable
data class AppDimen(
    val default: Dp,
    val screenPadding: Dp,
    val arrangementSpace: Dp,
    val extraSmallSpace: Dp,
    val smallSpace: Dp,
    val mediumSpace: Dp,
    val largeSpace: Dp,
    val iconSize: Dp,
    val smallIconSize: Dp,
    val cardElevation: Dp,
    val cardPadding: Dp
)

@Immutable
data class AppColorScheme(
    val primary: Color = Blue
)

@Immutable
data class AppShapes(
    val default: RoundedCornerShape,
    val small: RoundedCornerShape,
    val defaultTopCarved: RoundedCornerShape
)

@Immutable
data class AppTypography(
    val xLargeTitle: TextStyle,
    val largeTitle: TextStyle,
    val title1: TextStyle,
    val title2: TextStyle,
    val title3: TextStyle,
    val headline1: TextStyle,
    val headline2: TextStyle,
    val body: TextStyle,
    val bodyButton: TextStyle,
    val subheadline: TextStyle,
    val subheadlineButton: TextStyle,
    val callout: TextStyle,
    val calloutButton: TextStyle,
    val footnote: TextStyle,
    val footstrike: TextStyle,
    val caption1: TextStyle,
    val caption2: TextStyle
)

object AppTheme {
    val typography: AppTypography
        @Composable get() = LocalTypography.current
    val shapes: AppShapes
        @Composable get() = LocalShapes.current
    val dimen: AppDimen
        @Composable get() = LocalDimen.current
}

val LocalDimen = staticCompositionLocalOf {
    Dimen
}

val LocalTypography = staticCompositionLocalOf {
    Typography
}

val LocalShapes = staticCompositionLocalOf {
    Shapes
}

val LocalDateTimeFormatter = staticCompositionLocalOf<AppDateFormatter> {
    error("${AppDateFormatter::class.qualifiedName} not provided")
}

val LocalWindowSizeClass = staticCompositionLocalOf<WindowSizeClass> {
    error("${WindowSizeClass::class.qualifiedName} not provided")
}

@Composable
fun TherapistTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = AppColorScheme()
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val insets = WindowCompat.getInsetsController(window, view)
            window.statusBarColor = WhiteBackground.toArgb()
            // In API below 26 there were no dark icons in the navigation bar
            window.navigationBarColor = if (SDK_INT >= 26) {
                WhiteBackground.toArgb()
            } else GrayOverlay.toArgb()
            insets.isAppearanceLightStatusBars = !darkTheme
            insets.isAppearanceLightNavigationBars = darkTheme
        }
    }

    CompositionLocalProvider(
        LocalTypography provides Typography,
        LocalShapes provides Shapes,
        LocalDimen provides Dimen
    ) {
        MaterialTheme(content = content)
    }
}