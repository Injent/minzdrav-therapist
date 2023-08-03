package ru.minzdrav.therapist.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.spec.NavHostEngine
import ru.minzdrav.therapist.MainActivityViewModel
import ru.minzdrav.therapist.R
import ru.minzdrav.therapist.core.data.util.NetworkMonitor
import ru.minzdrav.therapist.core.designsystem.components.AppBackground
import ru.minzdrav.therapist.core.designsystem.components.AppBottomNavigationBar
import ru.minzdrav.therapist.core.designsystem.components.AppNavigationItem
import ru.minzdrav.therapist.core.designsystem.components.AppNavigationRail
import ru.minzdrav.therapist.core.designsystem.components.AppSnackbarHost
import ru.minzdrav.therapist.core.designsystem.components.AppSnackbarVisuals
import ru.minzdrav.therapist.core.designsystem.icon.AppIcons
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme
import ru.minzdrav.therapist.core.domain.model.AuthStatus
import ru.minzdrav.therapist.navigation.AppNavigation
import ru.minzdrav.therapist.navigation.BottomBarTab
import ru.minzdrav.therapist.navigation.NavGraphs

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialNavigationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun TherapistApp(
    viewModel: MainActivityViewModel,
    navHostEngine: NavHostEngine = rememberAnimatedNavHostEngine(),
    networkMonitor : NetworkMonitor,
    appState: TherapistAppState = rememberTherapistAppState(
        networkMonitor = networkMonitor,
        navController = navHostEngine.rememberNavController(),
        bottomSheetNavigator = rememberBottomSheetNavigator(),
    ),
) {
    val authStatus by viewModel.isSigned.collectAsStateWithLifecycle()

    val snackbarHost = remember { SnackbarHostState() }

    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

    val context = LocalContext.current

    var shouldShowNetworkConnectedAlert by remember { mutableStateOf(false) }

    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHost.showSnackbar(
                AppSnackbarVisuals(
                    duration = SnackbarDuration.Indefinite,
                    message = context.getString(R.string.alert_no_internet_connection),
                    withDismissAction = true,
                    closeIconResId = AppIcons.WarningRed
                ),
            )
        } else {
            if (!shouldShowNetworkConnectedAlert) {
                shouldShowNetworkConnectedAlert = true
                return@LaunchedEffect
            }
            snackbarHost.showSnackbar(
                AppSnackbarVisuals(
                    duration = SnackbarDuration.Short,
                    message = context.getString(R.string.alert_internet_connection_established),
                    withDismissAction = true,
                    closeIconResId = AppIcons.Done
                ),
            )
        }
    }

    // Preventing screen drawing before navigation is displayed
    var isNavigationDrawed by remember { mutableStateOf(false) }

    DisposableEffect(appState.navController) {
        val listener = NavController.OnDestinationChangedListener { _, _, _ ->
            isNavigationDrawed = false
        }
        appState.navController.addOnDestinationChangedListener(listener)

        onDispose {
            appState.navController.removeOnDestinationChangedListener(listener)
        }
    }

    if (!appState.shouldShowBottomBar && !appState.shouldShowNavRail) {
        isNavigationDrawed = true
    }

    AppBackground {
        Scaffold(
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                if (appState.shouldShowBottomBar) {
                    TherapistBottomBar(
                        destinations = appState.bottomBarTabs,
                        onNavigate = appState::navigate,
                        currentTab = appState.currentBottomBarTab,
                        modifier = Modifier
                            .fillMaxWidth()
                            .drawWithContent {
                                drawContent()
                                isNavigationDrawed = true
                            }
                    )
                }
            },
            snackbarHost = {
                AppSnackbarHost(
                    hostState = snackbarHost,
                    modifier = Modifier.padding(8.dp)
                )
            },
            modifier = Modifier
                .then(
                    if (appState.isPortraitMode) Modifier
                        .statusBarsPadding()
                        .navigationBarsPadding()
                    else Modifier
                )
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(
                            WindowInsetsSides.Horizontal
                        )
                    ),
            ) {
                if (appState.shouldShowNavRail) {
                    TherapistNavRail(
                        destinations = appState.bottomBarTabs,
                        onNavigate = appState::navigate,
                        currentTab = appState.currentBottomBarTab,
                        modifier = Modifier
                            .fillMaxHeight()
                            .drawWithContent {
                                drawContent()
                                isNavigationDrawed = true
                            }
                    )
                }

                val startRoute = when (authStatus) {
                    AuthStatus.AUTHED, AuthStatus.AUTHED_OFFLINE -> NavGraphs.root.startRoute
                    else -> NavGraphs.login
                }

                AppNavigation(
                    navHostEngine = navHostEngine,
                    appState = appState,
                    startRoute = startRoute,
                    modifier = Modifier.then(if (isNavigationDrawed) Modifier else Modifier.alpha(0f))
                )
            }
        }
    }
}

@Composable
fun TherapistBottomBar(
    destinations: List<BottomBarTab>,
    onNavigate: (BottomBarTab) -> Unit,
    currentTab: BottomBarTab?,
    modifier: Modifier = Modifier
) {
    AppBottomNavigationBar(
        modifier.shadow(elevation = AppTheme.dimen.cardElevation, clip = false)
    ) {
        for (tab in destinations) {
            AppNavigationItem(
                onClick = { onNavigate(tab) },
                iconResId = tab.icon,
                label = stringResource(tab.label),
                selected = currentTab == tab
            )
        }
    }
}

@Composable
fun TherapistNavRail(
    destinations: List<BottomBarTab>,
    onNavigate: (BottomBarTab) -> Unit,
    currentTab: BottomBarTab?,
    modifier: Modifier = Modifier
) {
    AppNavigationRail(
        contentPadding = WindowInsets.statusBars.asPaddingValues(),
        modifier = modifier
            .shadow(elevation = AppTheme.dimen.cardElevation, clip = false),
    ) {
        for (tab in destinations) {
            AppNavigationItem(
                onClick = { onNavigate(tab) },
                iconResId = tab.icon,
                label = stringResource(tab.label),
                selected = currentTab == tab
            )
        }
    }
}