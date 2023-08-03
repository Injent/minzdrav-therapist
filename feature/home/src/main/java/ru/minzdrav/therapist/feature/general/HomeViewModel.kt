package ru.minzdrav.therapist.feature.general

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.minzdrav.therapist.core.common.util.TextResource
import ru.minzdrav.therapist.core.data.util.SyncManager
import ru.minzdrav.therapist.core.domain.GetTherapistCallsUseCase
import ru.minzdrav.therapist.feature.home.R
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getTherapistCall: GetTherapistCallsUseCase,
    syncManager: SyncManager
) : ViewModel() {

    private val _therapistCallsSorting = MutableStateFlow(TherapistCallsSorting.ALL)
    val therapistCallsSorting: StateFlow<TherapistCallsSorting>
        get() = _therapistCallsSorting.asStateFlow()

    val isSyncing = syncManager.isSyncing
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false
        )

    val uiState: StateFlow<HomeUiState> = _therapistCallsSorting.flatMapLatest {
        homeUiState(getTherapistCall, _therapistCallsSorting.value)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = HomeUiState.Loading
        )

    fun onChangeSorting(sort: TherapistCallsSorting) {
        _therapistCallsSorting.value = sort
    }
}

// Enums order affects the order of the chips in the ui
enum class TherapistCallsSorting {
    ALL
}

private fun homeUiState(
    getTherapistCalls: GetTherapistCallsUseCase,
    sort: TherapistCallsSorting
): Flow<HomeUiState> {
    return try {
        getTherapistCalls()
            .map { calls ->
                val sortedCalls = when (sort) {
                    TherapistCallsSorting.ALL -> calls.sortedBy { it.status }
                }.toImmutableList()

                if (sortedCalls.isNotEmpty()) {
                    HomeUiState.Success(sortedCalls)
                } else {
                    HomeUiState.Empty
                }
            }
    } catch (e: Exception) {
        flowOf(HomeUiState.Error(TextResource.Id(R.string.error_failed_to_load_list)))
    }
}