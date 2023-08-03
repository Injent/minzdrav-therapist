package ru.minzdrav.therapist.navigation

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.ramcosta.composedestinations.dynamic.within
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.NavGraphSpec
import ru.minzdrav.therapist.feature.callforms.PatientOnboardingNavigator
import ru.minzdrav.therapist.feature.callforms.destinations.CommonInspectionScreenDestination
import ru.minzdrav.therapist.feature.callforms.destinations.PatientOnboardingDestination
import ru.minzdrav.therapist.feature.callforms.navigation.CallFormsNavigator
import ru.minzdrav.therapist.feature.documents.DocumentsNavigator
import ru.minzdrav.therapist.feature.general.HomeNavigator
import ru.minzdrav.therapist.feature.login.destinations.LoginOnboardingDestination
import ru.minzdrav.therapist.feature.login.destinations.SignInScreenDestination
import ru.minzdrav.therapist.feature.login.navigation.LoginNavigator
import ru.minzdrav.therapist.feature.note.destinations.NoteScreenDestination
import ru.minzdrav.therapist.feature.signup.SignUpNavigator
import ru.minzdrav.therapist.feature.signup.destinations.SignUpDestination

class CommonNavGraphNavigator(
    private val navGraph: NavGraphSpec,
    private val navController: NavController
) : LoginNavigator,
    SignUpNavigator,
    PatientOnboardingNavigator,
    HomeNavigator,
    DocumentsNavigator,
    CallFormsNavigator {

    override fun navigateToSignIn() {
        navController.navigate(SignInScreenDestination within navGraph)
    }

    override fun navigateUp() {
        navController.navigateUp()
    }

    override fun navigateToSignUp() {
        navController.navigate(SignUpDestination within navGraph)
    }

    override fun navigateToGeneral() {
        navController.navigate(NavGraphs.general) {
            popUpTo(NavGraphs.general.route) {
                inclusive = true
            }
        }
    }

    override fun navigateToCommonInspection() {
        navController.navigate(CommonInspectionScreenDestination within navGraph)
    }

    override fun navigateToLoginOnboarding() {
        navController.navigate(LoginOnboardingDestination within navGraph)
    }

    override fun navigateToCallForms(therapistCallId: Long) {
        if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
            navController.navigate(PatientOnboardingDestination(therapistCallId) within navGraph)
        }
    }

    override fun navigateToNote(title: String, placeholder: String) {
        if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
            navController.navigate(NoteScreenDestination(title, placeholder) within navGraph)
        }
    }
}