package ru.minzdrav.therapist.feature.general

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import ru.minzdrav.therapist.core.designsystem.components.AppChip
import ru.minzdrav.therapist.core.designsystem.components.BasicLayout
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme
import ru.minzdrav.therapist.core.ui.therapistCallsItems
import ru.minzdrav.therapist.feature.home.R

interface HomeNavigator {
    fun navigateUp()
    fun navigateToLoginOnboarding()
    fun navigateToCallForms(therapistCallId: Long)
}

@Destination
@Composable
fun HomeScreen(
    navigator: HomeNavigator
) {
    val viewModel: HomeViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sorting by viewModel.therapistCallsSorting.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        sorting = sorting,
        onChangeSorting = viewModel::onChangeSorting,
        onSelectTherapistCall = navigator::navigateToCallForms
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState,
    sorting: TherapistCallsSorting,
    onChangeSorting: (TherapistCallsSorting) -> Unit,
    onSelectTherapistCall: (Long) -> Unit,
) {
    BasicLayout(topBar = { /*TODO*/ }) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            sortingChips(
                sorting = TherapistCallsSorting.values().asList(),
                selectedSorting = sorting,
                onSelect = onChangeSorting
            )

            when (uiState) {
                is HomeUiState.Success -> {
                    therapistCallsItems(
                        items = uiState.calls,
                        onItemClick = onSelectTherapistCall
                    )
                }
                HomeUiState.Empty -> item { Text(text = "Empty") }
                is HomeUiState.Error -> item { Text(text = "Error") }
                HomeUiState.Loading -> item { Text(text = "Loading") }
            }
        }
    }
}

private fun LazyListScope.sortingChips(
    sorting: List<TherapistCallsSorting>,
    selectedSorting: TherapistCallsSorting,
    onSelect: (TherapistCallsSorting) -> Unit,
    modifier: Modifier = Modifier
) = item {
    val scrollState = rememberScrollState()
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimen.arrangementSpace),
        modifier = modifier.horizontalScroll(scrollState)
    ) {
        Spacer(Modifier.width(AppTheme.dimen.screenPadding))
        sorting.forEach {
            AppChip(
                label = stringResource(R.string.chip_label_all),
                onClick = { onSelect(it) },
                selected = it == selectedSorting
            )
        }
        Spacer(Modifier.width(AppTheme.dimen.screenPadding))
    }
}