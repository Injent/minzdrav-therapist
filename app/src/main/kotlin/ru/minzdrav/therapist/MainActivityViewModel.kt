package ru.minzdrav.therapist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.minzdrav.therapist.core.data.util.SyncManager
import ru.minzdrav.therapist.core.domain.GetAuthStatusUseCase
import ru.minzdrav.therapist.core.domain.model.AuthStatus
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    syncManager: SyncManager,
    getAuthStatus: GetAuthStatusUseCase,

) : ViewModel() {

    val isSigned = getAuthStatus()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = AuthStatus.PENDING
        )

    init {
        viewModelScope.launch {
            combine(isSigned, syncManager.isSyncRequired) { isSigned, isSyncRequired ->
                if (isSigned == AuthStatus.AUTHED && isSyncRequired) syncManager.requestSync()
            }.collect()
        }
    }
}