package ru.minzdrav.therapist.feature.callforms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.OpenResultRecipient
import ru.minzdrav.therapist.core.designsystem.components.AppCircularLoading
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme
import ru.minzdrav.therapist.core.designsystem.theme.BlueCobalt
import ru.minzdrav.therapist.core.designsystem.theme.RedBlush
import ru.minzdrav.therapist.core.model.forms.ViewData
import ru.minzdrav.therapist.core.ui.util.asString
import ru.minzdrav.therapist.feature.callforms.navigation.CallFormsNavigator
import ru.minzdrav.therapist.feature.callforms.state.UiSchemaState

internal const val CommonInspectionsScreenId = "common_inspections"

@Destination
@Composable
fun CommonInspectionScreen(
    resultRecipient: OpenResultRecipient<String>,
    navigator: CallFormsNavigator
) {
    val viewModel: CallFormsViewModel = hiltViewModel()
        //val uiSchemaState by viewModel.uiSchemaState.collectAsStateWithLifecycle()

    resultRecipient.onNavResult { text ->

    }

//    CommonInspectionScreen(
//        uiSchemaState = uiSchemaState,
//        onDataChange = viewModel::onDataChange
//    )
}

@Composable
private fun CommonInspectionScreen(
    uiSchemaState: UiSchemaState,
    onDataChange: (key: String, data: ViewData<*>) -> Unit
) {
    when (uiSchemaState) {
        is UiSchemaState.Error -> Box(Modifier.fillMaxSize()) {
            Text(
                text = uiSchemaState.errorText.asString(),
                color = RedBlush,
                style = AppTheme.typography.callout,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        UiSchemaState.Loading -> Box(Modifier.fillMaxSize()) {
            AppCircularLoading(
                modifier = Modifier.align(Alignment.Center),
                tint = BlueCobalt
            )
        }
        is UiSchemaState.Success -> {
            uiSchemaState.schemas[CommonInspectionsScreenId]?.let {


                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        horizontal = AppTheme.dimen.screenPadding + AppTheme.dimen.cardPadding
                    ),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.dimen.arrangementSpace)
                ) {

                }
            }
        }
    }
}