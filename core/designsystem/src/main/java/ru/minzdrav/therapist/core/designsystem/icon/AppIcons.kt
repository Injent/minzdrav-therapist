package ru.minzdrav.therapist.core.designsystem.icon

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector
import ru.minzdrav.therapist.core.designsystem.R

object AppIcons {
    val Home = R.drawable.ic_home
    val Back = R.drawable.ic_back
    val CloseSmall = R.drawable.ic_close_small
    val WarningRed = R.drawable.ic_warning_red
    val Doc = R.drawable.ic_doc
    val Done = R.drawable.ic_done
    val SmallArrowNext = R.drawable.ic_small_arrow_next
}

/**
 * A sealed class to make dealing with [ImageVector] and [DrawableRes] icons easier.
 */
sealed class Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
    data class DrawableResourceIcon(@DrawableRes val id: Int) : Icon()
}