package ru.minzdrav.therapist.core.ui.serverdriven

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import ru.minzdrav.therapist.core.model.forms.ViewKind

@Stable
data class ServerDrivenUiState internal constructor(
    private val elements: MutableMap<String, ViewElement>
) {
    val views: ImmutableList<ViewElement> get() = elements.values.toImmutableList()

    fun setData(elementId: String, data: Any?) {
        elements[elementId]?.let {
            elements[elementId] = it.copy(kind = it.kind, data = data)
        }
    }
}

internal fun ServerDrivenUiState(elements: List<ViewKind>): ServerDrivenUiState {
    return ServerDrivenUiState(
        elements = elements.associate { it.id to ViewElement(it, null) }.toMutableMap()
    )
}