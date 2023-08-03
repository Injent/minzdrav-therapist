package ru.minzdrav.therapist.core.ui.util

import android.content.Context
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import ru.minzdrav.therapist.core.common.util.TextResource
import ru.minzdrav.therapist.core.model.forms.ViewKind

@Composable
fun TextResource.asString(): String {
    return when (this) {
        is TextResource.Id -> stringResource(resId)
        is TextResource.Plain -> plain
        is TextResource.DynamicString -> stringResource(resId, *args)
    }
}

fun TextResource.asString(context: Context): String {
    return when (this) {
        is TextResource.Id -> context.getString(resId)
        is TextResource.Plain -> plain
        is TextResource.DynamicString -> context.getString(resId, *args)
    }
}

val ViewKind.TextField.keyboardOptions: KeyboardOptions
    get() = when (inputType) {
        ViewKind.TextField.InputType.TEXT -> KeyboardOptions(
            keyboardType = KeyboardType.Text,
            autoCorrect = autoCorrect
        )
        ViewKind.TextField.InputType.NUMBER -> KeyboardOptions(keyboardType = KeyboardType.Number)
        ViewKind.TextField.InputType.DECIMAL -> KeyboardOptions(keyboardType = KeyboardType.Decimal)
    }