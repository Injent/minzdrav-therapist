package ru.minzdrav.therapist.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import ru.minzdrav.therapist.core.designsystem.components.AppCard
import ru.minzdrav.therapist.core.designsystem.components.TextWithStatus
import ru.minzdrav.therapist.core.designsystem.icon.AppIcons
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme
import ru.minzdrav.therapist.core.designsystem.theme.BlackText
import ru.minzdrav.therapist.core.designsystem.theme.Gray1
import ru.minzdrav.therapist.core.designsystem.theme.GrayDark
import ru.minzdrav.therapist.core.designsystem.theme.GreenSuccess
import ru.minzdrav.therapist.core.designsystem.theme.LocalDateTimeFormatter
import ru.minzdrav.therapist.core.designsystem.theme.RedTheme
import ru.minzdrav.therapist.core.designsystem.theme.TherapistTheme
import ru.minzdrav.therapist.core.designsystem.theme.Yellow
import ru.minzdrav.therapist.core.model.CallStatus
import ru.minzdrav.therapist.core.ui.model.UiTherapistCall

@Composable
fun TherapistCallCard(
    therapistCall: UiTherapistCall,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppCard(
        modifier = modifier,
        elevation = 0.dp,
        onClick = onClick,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimen.arrangementSpace)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = therapistCall.age.toString(),
                    style = AppTheme.typography.footnote,
                    color = GrayDark,
                    maxLines = 1
                )

                val dateTimeFormatter = LocalDateTimeFormatter.current

                Text(
                    text = dateTimeFormatter.formatNumericDateTime(therapistCall.registrationDate),
                    style = AppTheme.typography.footnote,
                    color = GrayDark,
                    maxLines = 1
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = therapistCall.address,
                    style = AppTheme.typography.body,
                    color = BlackText
                )
                Icon(
                    painter = painterResource(AppIcons.SmallArrowNext),
                    contentDescription = null,
                    tint = Gray1
                )
            }

            Text(
                text = therapistCall.fullName,
                style = AppTheme.typography.callout,
                color = GrayDark,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Spacer(Modifier.height(AppTheme.dimen.extraSmallSpace))

            TextWithStatus(
                statusColor = when (therapistCall.status) {
                    CallStatus.INSPECTED -> GreenSuccess
                    CallStatus.AWAITS -> Yellow
                    CallStatus.EMERGENCY -> RedTheme
                }
            ) {
                val callStatus = stringResource(
                    when (therapistCall.status) {
                        CallStatus.INSPECTED -> R.string.call_status_inspected
                        CallStatus.AWAITS -> R.string.call_status_awaits
                        CallStatus.EMERGENCY -> R.string.call_status_emergency
                    }
                )

                Text(
                    text = callStatus,
                    style = AppTheme.typography.callout,
                    color = GrayDark,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview
@Composable
private fun TherapistCallCardPreview() {
    val call = UiTherapistCall(
        id = 0,
        fullName = "John Doe",
        34,
        status = CallStatus.AWAITS,
        address = "Omsk, 70 let Oktabrya, block 3/2, f. 18",
        registrationDate = Clock.System.now()
    )
    TherapistTheme {
        TherapistCallCard(
            therapistCall = call,
            onClick = {}
        )
    }
}