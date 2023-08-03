package ru.minzdrav.therapist.feature.callforms

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import ru.minzdrav.therapist.core.common.util.TextResource
import ru.minzdrav.therapist.core.data.repository.ServerDrivenUiRepository
import ru.minzdrav.therapist.core.model.forms.ViewData
import ru.minzdrav.therapist.core.model.forms.ViewKind
import ru.minzdrav.therapist.feature.callforms.state.UiSchemaState
import javax.inject.Inject

@HiltViewModel
class CallFormsViewModel @Inject constructor(
    private val serverDrivenUiRepository: ServerDrivenUiRepository
) : ViewModel() {
    private val uiElements = mutableMapOf<String, MutableMap<ViewKind, ViewData<*>>>()

    fun getUiElementsForScreen(screenId: String): ImmutableMap<ViewKind, ViewData<*>> {
        return uiElements[screenId]?.toImmutableMap() ?: persistentMapOf()
    }

    fun setData(screenId: String, element: ViewKind, value: ViewData<*>) {
        uiElements[screenId]?.set(element, value)
    }
}

private suspend fun getUiSchemas(
    uiRepo: ServerDrivenUiRepository
): UiSchemaState = coroutineScope {
    runCatching {
        val uiSchemas = awaitAll(
            async {
                CommonInspectionsScreenId to uiRepo.getUiSchema(CommonInspectionsScreenId)
            }
        ).toMap()

        UiSchemaState.Success(uiSchemas)
    }.getOrElse {
        UiSchemaState.Error(TextResource.Plain(it.localizedMessage ?: "Unknown error"))
    }
}

