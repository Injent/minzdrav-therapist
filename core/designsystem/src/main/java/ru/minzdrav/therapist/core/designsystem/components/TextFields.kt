package ru.minzdrav.therapist.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.minzdrav.therapist.core.designsystem.R
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme
import ru.minzdrav.therapist.core.designsystem.theme.BlackText
import ru.minzdrav.therapist.core.designsystem.theme.Blue
import ru.minzdrav.therapist.core.designsystem.theme.LightBlue
import ru.minzdrav.therapist.core.designsystem.theme.BlueWave
import ru.minzdrav.therapist.core.designsystem.theme.Destructive
import ru.minzdrav.therapist.core.designsystem.theme.Gray1
import ru.minzdrav.therapist.core.designsystem.theme.Gray2
import ru.minzdrav.therapist.core.designsystem.theme.GrayDark
import ru.minzdrav.therapist.core.designsystem.theme.GrayDisabled
import ru.minzdrav.therapist.core.designsystem.theme.GrayFooter
import ru.minzdrav.therapist.core.designsystem.theme.RedPalePink
import ru.minzdrav.therapist.core.designsystem.theme.TherapistTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AppTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placeholder: String = "",
    readOnly: Boolean = false,
    textStyle: TextStyle = AppTheme.typography.callout,
    singleLine: Boolean = false,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: (@Composable () -> Unit)? = null,
    shape: Shape = AppTheme.shapes.default,
    tip: String? = null,
    error: String? = null,
    link: String? = null,
    label: String? = null,
    outlined: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        label?.let { label ->
            Text(
                text = label,
                style = AppTheme.typography.callout,
                color = BlackText,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        val interactionSource = remember { MutableInteractionSource() }
        val isFocused by interactionSource.collectIsFocusedAsState()

        val borderColor = remember(isFocused, isError, outlined, enabled) {
            when {
                isError -> Destructive
                outlined && !enabled -> GrayDisabled
                outlined && isFocused -> GrayFooter
                outlined && !isFocused -> GrayFooter
                else -> Color.Transparent
            }
        }
        val borderModifier = if (borderColor != Color.Transparent)
            Modifier.border(1.2.dp, borderColor, shape)
        else Modifier

        val focusManager = LocalFocusManager.current
        val isImeVisible = WindowInsets.isImeVisible
        LaunchedEffect(isImeVisible) {
            if (!isImeVisible)
                focusManager.clearFocus()
        }

        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            interactionSource = interactionSource,
            textStyle = textStyle,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(40.dp)
                .then(borderModifier)
                .background(
                    color = when {
                        isFocused && enabled -> BlueWave
                        !enabled -> GrayFooter
                        else -> LightBlue
                    },
                    shape = shape
                )
        ) { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = text,
                innerTextField = innerTextField,
                enabled = enabled,
                singleLine = singleLine,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                isError = isError,
                contentPadding = PaddingValues(
                    top = 10.dp, bottom = 12.dp,
                    start = 12.dp, end = 12.dp
                ),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = LightBlue,
                    unfocusedPlaceholderColor = Gray2,
                    unfocusedLeadingIconColor = Gray1,
                    unfocusedTrailingIconColor = Gray1,
                    unfocusedIndicatorColor = Color.Transparent,

                    disabledContainerColor = GrayFooter,
                    disabledPlaceholderColor = Gray2,
                    disabledLeadingIconColor = Gray2,
                    disabledTrailingIconColor = Gray2,
                    disabledIndicatorColor = Color.Transparent,

                    focusedContainerColor = BlueWave,
                    focusedPlaceholderColor = Gray2,
                    focusedTextColor = BlackText,
                    focusedLeadingIconColor = Gray1,
                    focusedTrailingIconColor = Gray1,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = Blue,

                    errorContainerColor = RedPalePink,
                    errorLeadingIconColor = Gray1,
                    errorTrailingIconColor = Gray1,
                    errorIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = placeholder,
                        style = AppTheme.typography.callout
                    )
                },
                shape = shape,
                trailingIcon = trailingIcon,
            )
        }

        error?.let { error ->
            Text(
                text = error,
                color = Destructive,
                style = AppTheme.typography.footnote
            )
        }

        tip?.let { tip ->
            Text(
                text = tip,
                color = GrayDark,
                style = AppTheme.typography.footnote,
            )
        }

        link?.let { link ->
            Text(
                text = link,
                color = Blue,
                style = AppTheme.typography.footnote
            )
        }
    }
}

@Composable
fun AppReadOnlyTextField(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    singleLine: Boolean = false,
    isError: Boolean = false,
    trailingIcon: (@Composable () -> Unit)? = null,
    shape: Shape = AppTheme.shapes.default,
    tip: String? = null,
    error: String? = null,
    link: String? = null,
    label: String? = null,
) {
    Box {
        AppTextField(
            text = text,
            onValueChange = {},
            modifier = modifier,
            placeholder = placeholder,
            singleLine = singleLine,
            isError = isError,
            trailingIcon = trailingIcon,
            shape = shape,
            tip = tip,
            error = error,
            link = link,
            label = label,
            readOnly = true
        )
        Box(
            Modifier
                .matchParentSize()
                .clickable(onClick = onClick)
        )
    }
}

@Composable
fun EditableText(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    style: TextStyle = AppTheme.typography.body,
    shape: Shape = RectangleShape,
    contentPadding: PaddingValues = PaddingValues(AppTheme.dimen.cardPadding),
    placeholder: String = ""
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = style,
        modifier = modifier.background(backgroundColor, shape),
    ) { innerTextField ->
        Box(Modifier.padding(contentPadding)) {
            if (value.isNotEmpty()) {
                innerTextField()
            } else {
                Text(text = placeholder, style = style.copy(color = Gray2))
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF,
    widthDp = 340
)
@Composable
private fun DefaultAppTextFieldPreview() {
    TherapistTheme {
        var value by remember { mutableStateOf("Text") }

        AppTextField(
            text = value,
            onValueChange = { value = it },
            label = "Field name",
            placeholder = "Placeholder",
            trailingIcon = {
                AppIconButton(onClick = {}, icon = R.drawable.ic_close)
            },
            error = "Error text",
            tip = "Tip text",
            link = "Link text",
            modifier = Modifier.padding(AppTheme.dimen.default)
        )
    }
}

@Composable
fun AppDatePickerTextField(
    date: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = true,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    shape: Shape = AppTheme.shapes.default,
    tip: String? = null,
    error: String? = null,
    link: String? = null,
    label: String? = null,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        AppTextField(
            text = date,
            onValueChange = onValueChange,
            enabled = enabled,
            placeholder = stringResource(R.string.textfield_date_format_placeholder),
            readOnly = readOnly,
            singleLine = true,
            isError = isError,
            visualTransformation = visualTransformation,
            shape = shape,
            tip = tip,
            error = error,
            link = link,
            label = label,
            trailingIcon = {
                if (date.isNotEmpty()) {
                    AppIconButton(
                        onClick = {},
                        icon = R.drawable.ic_close
                    )
                }
            },
            modifier = Modifier.weight(1f)
        )

        val calendarState by rememberCalendarState()

        AppFilledIconButton(
            onClick = { calendarState },
            icon = R.drawable.ic_calendar,
            shape = shape
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF,
    widthDp = 340
)
@Composable
private fun DefaultAppDatePickerTextFieldPreview() {
    TherapistTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            AppDatePickerTextField(
                date = "01.03.2023",
                onValueChange = {}
            )
            AppDatePickerTextField(
                date = "",
                onValueChange = {}
            )
        }
    }
}