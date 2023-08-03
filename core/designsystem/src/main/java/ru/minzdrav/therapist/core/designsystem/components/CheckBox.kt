package ru.minzdrav.therapist.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.minzdrav.therapist.core.designsystem.R
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme
import ru.minzdrav.therapist.core.designsystem.theme.BlackText
import ru.minzdrav.therapist.core.designsystem.theme.Blue
import ru.minzdrav.therapist.core.designsystem.theme.BlueChateau
import ru.minzdrav.therapist.core.designsystem.theme.Gray1
import ru.minzdrav.therapist.core.designsystem.theme.LightBlue
import ru.minzdrav.therapist.core.designsystem.theme.TherapistTheme
import ru.minzdrav.therapist.core.designsystem.theme.WhiteCard
import ru.minzdrav.therapist.core.designsystem.theme.WhiteText

@Composable
fun AppCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    text: String? = null,
    shape: Shape = RoundedCornerShape(4.dp),
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }

    val (checkedColor, checkColor, borderColor) = when {
        checked && enabled -> arrayOf(Blue, WhiteText, null)
        !checked && enabled -> arrayOf(WhiteCard, null, Gray1)
        checked && !enabled -> arrayOf(Gray1, LightBlue, null)
        else -> arrayOf(BlueChateau, null, Gray1)
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(checkedColor!!, shape)
                .then(
                    borderColor?.let {
                        Modifier.border(1.dp, it, shape)
                    } ?: Modifier
                )
                .clickable(
                    enabled = enabled,
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { onCheckedChange(!checked) }
                )
                .padding(1.dp)
        ) {
            checkColor?.let {
                Icon(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = null,
                    tint = it,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(20.dp)
                )
            }
        }
        text?.let {
            Text(
                text = it,
                style = AppTheme.typography.callout,
                color = BlackText,
                modifier = Modifier.padding(top = 1.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppCheckBoxPreview() {
    TherapistTheme {
        Row(Modifier.padding(AppTheme.dimen.screenPadding)) {
            AppCheckBox(
                checked = false,
                onCheckedChange = {},
                text = "Checkbox text"
            )
        }
    }
}