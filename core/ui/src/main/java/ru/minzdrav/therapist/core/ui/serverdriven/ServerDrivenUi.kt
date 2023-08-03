package ru.minzdrav.therapist.core.ui.serverdriven

import androidx.compose.runtime.Stable
import ru.minzdrav.therapist.core.model.forms.ViewKind

@Stable
data class ViewElement(
    val kind: ViewKind,
    val data: Any? = null
)

enum class ViewElementAction {
    OPEN_NOTE
}

// Commented for commit

//@Stable
//fun LazyListScope.serverDrivenUi(
//    state: ServerDrivenUiState,
//    onActionRequest: (elementId: String, ViewElementAction) -> Unit
//) = items(
//    items = state.views,
//    key = { it.kind.id }
//) { view ->
//    when (view.kind) {
//        is ViewKind.CheckBox -> UiCheckBox(
//            element = view,
//            onValueChange = { value -> state.onDataChange(view.id, value) }
//        )
//        is ViewKind.Spinner -> TODO()
//        is ViewKind.TextField -> UiTextField(
//            element = view,
//            onValueChange = { value -> state.onDataChange(view.id, value) },
//            onActionRequest = { onActionRequest(view.id, it) },
//            data = { state.getData(view.id) }
//        )
//    }
//}

//@Composable
//private fun UiTextField(
//    data: () -> ViewData<String>?,
//    element: ViewElement,
//    onValueChange: (ViewData<String>) -> Unit,
//    onActionRequest: (ViewElementAction) -> Unit
//) {
//    val elementData = remember(element) { element.kind as? ViewKind.TextField }
//    val text = remember(data) { data()?.value ?: "" }
//
//    if (elementData.noteIsRequired) {
//        AppReadOnlyTextField(
//            text = text,
//            onClick = { onActionRequest(ViewElementAction.OPEN_NOTE) },
//            placeholder = elementData.placeholder,
//            label = elementData.label,
//            tip = elementData.tip,
//            singleLine = elementData.singleLine,
//        )
//    } else {
//        AppTextField(
//            text = text,
//            onValueChange = { onValueChange(ViewData(it)) },
//            placeholder = elementData.placeholder,
//            label = elementData.label,
//            tip = elementData.tip,
//            keyboardOptions = elementData.keyboardOptions,
//            singleLine = elementData.singleLine
//        )
//    }
//}

//@Composable
//private fun UiCheckBox(
//    element: ViewElement,
//    onValueChange: (ViewData<Boolean>) -> Unit
//) {
//    val view = remember(element) { element.kind as? ViewKind.CheckBox }
//    var checked by remember { mutableStateOf(element.data as? Boolean ?: false) }
//
//    AppCheckBox(
//        checked = checked,
//        onCheckedChange = { checked = it; onValueChange(ViewData(it)) }
//    )
//}