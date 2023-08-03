package ru.minzdrav.therapist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.minzdrav.therapist.core.common.util.AppDateFormatter
import ru.minzdrav.therapist.core.data.util.NetworkMonitor
import ru.minzdrav.therapist.core.designsystem.theme.LocalDateTimeFormatter
import ru.minzdrav.therapist.core.designsystem.theme.LocalWindowSizeClass
import ru.minzdrav.therapist.core.designsystem.theme.TherapistTheme
import ru.minzdrav.therapist.core.domain.model.AuthStatus
import ru.minzdrav.therapist.core.storage.UserPreferencesDataSource
import ru.minzdrav.therapist.ui.TherapistApp
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var networkMonitor: NetworkMonitor

    @Inject lateinit var appDateFormatter: AppDateFormatter

    @Inject lateinit var userPreferencesDataSource: UserPreferencesDataSource

    private val viewModel by viewModels<MainActivityViewModel>()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        super.onCreate(savedInstanceState)

        var authStatus by mutableStateOf(AuthStatus.PENDING)

        lifecycleScope.launch {
            userPreferencesDataSource.init()

            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isSigned
                    .onEach {
                        authStatus = it
                    }
                    .collect()
            }
        }

        splash.setKeepOnScreenCondition {
            authStatus == AuthStatus.PENDING
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            val windowSizeClass = calculateWindowSizeClass(this)

            DisposableEffect(Unit) {
                systemUiController.systemBarsBehavior = BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                systemUiController.isSystemBarsVisible =
                    windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

                onDispose {}
            }

            CompositionLocalProvider(
                LocalDateTimeFormatter provides appDateFormatter,
                LocalWindowSizeClass provides windowSizeClass
            ) {
                if (authStatus != AuthStatus.PENDING) {
                    TherapistContent()
                } else {
                    // Is needed to calculate insets while the splash screen is displayed
                    Box(Modifier.fillMaxSize())
                }
            }
        }
    }

    @Composable
    private fun TherapistContent() {
        TherapistTheme {
            TherapistApp(
                viewModel = viewModel,
                networkMonitor = networkMonitor
            )
        }
    }
}