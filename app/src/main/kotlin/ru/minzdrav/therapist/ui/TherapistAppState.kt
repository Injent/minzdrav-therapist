package ru.minzdrav.therapist.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.navigation.navigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.minzdrav.therapist.core.data.util.NetworkMonitor
import ru.minzdrav.therapist.core.designsystem.theme.LocalWindowSizeClass
import ru.minzdrav.therapist.navigation.BottomBarTab
import ru.minzdrav.therapist.navigation.NavGraphs

@OptIn(ExperimentalMaterialNavigationApi::class)
@Stable
data class TherapistAppState(
    val navController: NavHostController,
    val bottomSheetNavigator: BottomSheetNavigator,
    val coroutineScope: CoroutineScope,
    val networkMonitor: NetworkMonitor
) {
    init {
        navController.navigatorProvider += bottomSheetNavigator
    }

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false
        )

    private val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination
    
    val bottomBarTabs: List<BottomBarTab> = BottomBarTab.values().asList()

    private val bottomBarRoutes = bottomBarTabs.map { it.direction.route }

    val currentBottomBarTab: BottomBarTab?
        @Composable get() = bottomBarTabs.find {
            it.direction.route == currentDestination?.route?.substringAfterLast('/')
        }

    private val shouldShowNavigation: Boolean
        @Composable
        get() = currentDestination?.route?.substringAfterLast('/') in bottomBarRoutes

    val isPortraitMode: Boolean
        @Composable
        get() {
            val windowSizeClass = LocalWindowSizeClass.current
            return windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
        }

    val shouldShowBottomBar: Boolean
        @Composable
        get() = shouldShowNavigation && isPortraitMode

    val shouldShowNavRail: Boolean
        @Composable
        get() = shouldShowNavigation && !isPortraitMode

    fun navigate(destination: BottomBarTab) {
        val navGraph = when (destination) {
            BottomBarTab.HOME -> NavGraphs.general
            BottomBarTab.DOCUMENTS -> NavGraphs.documents
        }
        navController.navigate(navGraph) {
            launchSingleTop = true
            restoreState = true

            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun rememberTherapistAppState(
    navController: NavHostController,
    bottomSheetNavigator: BottomSheetNavigator,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    networkMonitor: NetworkMonitor
): TherapistAppState {
    return remember(
        navController,
        coroutineScope,
        bottomSheetNavigator
    ) {
        TherapistAppState(
            navController,
            bottomSheetNavigator,
            coroutineScope,
            networkMonitor
        )
    }
}