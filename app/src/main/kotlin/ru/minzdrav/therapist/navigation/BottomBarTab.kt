package ru.minzdrav.therapist.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import ru.minzdrav.therapist.R
import ru.minzdrav.therapist.core.designsystem.icon.AppIcons
import ru.minzdrav.therapist.feature.documents.destinations.DocumentsScreenDestination
import ru.minzdrav.therapist.feature.general.destinations.HomeScreenDestination

enum class BottomBarTab(
    val direction: DirectionDestinationSpec,
    @DrawableRes val icon: Int,
    @StringRes val label: Int
) {
    HOME(
        direction = HomeScreenDestination,
        icon = AppIcons.Home,
        label = R.string.destination_home
    ),
    DOCUMENTS(
        direction = DocumentsScreenDestination,
        icon = AppIcons.Doc,
        label = R.string.destination_docs
    )
}