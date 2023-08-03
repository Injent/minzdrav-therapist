package ru.minzdrav.therapist.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.dynamic.routedIn
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.DependenciesContainerBuilder
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.scope.AnimatedDestinationScope
import com.ramcosta.composedestinations.scope.resultRecipient
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.NavHostEngine
import com.ramcosta.composedestinations.spec.Route
import ru.minzdrav.therapist.feature.callforms.CommonInspectionScreen
import ru.minzdrav.therapist.feature.callforms.destinations.CommonInspectionScreenDestination
import ru.minzdrav.therapist.feature.callforms.destinations.PatientOnboardingDestination
import ru.minzdrav.therapist.feature.documents.destinations.DocumentsScreenDestination
import ru.minzdrav.therapist.feature.general.destinations.HomeScreenDestination
import ru.minzdrav.therapist.feature.login.destinations.LoginOnboardingDestination
import ru.minzdrav.therapist.feature.login.destinations.SignInScreenDestination
import ru.minzdrav.therapist.feature.note.destinations.NoteScreenDestination
import ru.minzdrav.therapist.ui.TherapistAppState

object NavGraphs {
    val login = object : NavGraphSpec {
        override val route = "login"

        override val startRoute = LoginOnboardingDestination routedIn this

        override val destinationsByRoute = destinations(
            LoginOnboardingDestination,
            SignInScreenDestination
        )
    }

    val general = object : NavGraphSpec {
        override val route = "general"

        override val startRoute = HomeScreenDestination routedIn this

        override val destinationsByRoute = destinations(
            HomeScreenDestination,
            PatientOnboardingDestination,
            CommonInspectionScreenDestination,
            NoteScreenDestination
        )
    }

    val documents = object : NavGraphSpec {
        override val route = "documents"

        override val startRoute = DocumentsScreenDestination routedIn this

        override val destinationsByRoute = destinations(
            DocumentsScreenDestination
        )
    }

    val root = object : NavGraphSpec {
        override val route = "root"

        override val startRoute = general

        override val destinationsByRoute = emptyMap<String, DestinationSpec<*>>()

        override val nestedNavGraphs = listOf(
            login,
            general,
            documents
        )
    }
}

private fun NavGraphSpec.destinations(vararg destinations: DestinationSpec<*>): Map<String, DestinationSpec<*>> {
    return destinations.toList()
        .routedIn(this)
        .associateBy { it.route }
}

fun NavDestination.navGraph(): NavGraphSpec {
    hierarchy.forEach { destination ->
        NavGraphs.root.nestedNavGraphs.forEach { navGraph ->
            if (destination.route == navGraph.route) {
                return navGraph
            }
        }
    }

    throw RuntimeException("Unknown nav graph for destination $route")
}

private fun DependenciesContainerBuilder<*>.currentNavigator(): CommonNavGraphNavigator {
    return CommonNavGraphNavigator(
        navBackStackEntry.destination.navGraph(),
        navController
    )
}

private fun AnimatedDestinationScope<Unit>.currentNavigator(): CommonNavGraphNavigator {
    return CommonNavGraphNavigator(
        navBackStackEntry.destination.navGraph(),
        navController
    )
}

private fun Collection<NavBackStackEntry>.print(prefix: String = "AppNavigation") {
    val stack = map { it.destination.route }.toTypedArray().contentToString()
    println("$prefix = $stack")
}

@SuppressLint("RestrictedApi")
@Composable
internal fun AppNavigation(
    navHostEngine: NavHostEngine,
    appState: TherapistAppState,
    startRoute: Route,
    modifier: Modifier = Modifier
) {
    appState.navController.currentBackStack.collectAsState().value.print()

    DestinationsNavHost(
        engine = navHostEngine,
        navController = appState.navController,
        navGraph = NavGraphs.root,
        startRoute = startRoute,
        modifier = modifier,
        dependenciesContainerBuilder = {
            dependency(currentNavigator())
        }
    ) {
        composable(CommonInspectionScreenDestination) {
            CommonInspectionScreen(
                navigator = currentNavigator(),
                resultRecipient = resultRecipient<NoteScreenDestination, String>()
            )
        }
    }
}