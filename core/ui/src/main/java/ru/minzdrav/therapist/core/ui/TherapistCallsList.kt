package ru.minzdrav.therapist.core.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.toImmutableList
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme
import ru.minzdrav.therapist.core.designsystem.theme.BlueChateau
import ru.minzdrav.therapist.core.model.TherapistCall
import ru.minzdrav.therapist.core.ui.model.UiTherapistCall
import ru.minzdrav.therapist.core.ui.model.asUiModel

fun LazyListScope.therapistCallsItems(
    items: List<TherapistCall>,
    onItemClick: (id: Long) -> Unit,
    itemModifier: Modifier = Modifier
) = items(
    items = items.map(TherapistCall::asUiModel).toImmutableList(),
    key = UiTherapistCall::id,
    itemContent = { therapistCall ->
        TherapistCallCard(
            therapistCall = therapistCall,
            onClick = { onItemClick(therapistCall.id) },
            modifier = itemModifier
        )

        Divider(
            color = BlueChateau,
            modifier = Modifier
                .padding(horizontal = AppTheme.dimen.cardPadding)
        )
    },
)