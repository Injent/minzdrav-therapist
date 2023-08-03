package ru.minzdrav.therapist.feature.note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import ru.minzdrav.therapist.core.designsystem.components.AppTopBar
import ru.minzdrav.therapist.core.designsystem.components.AppTopBarAction
import ru.minzdrav.therapist.core.designsystem.components.EditableText
import ru.minzdrav.therapist.core.designsystem.icon.AppIcons
import ru.minzdrav.therapist.core.designsystem.theme.AppTheme
import ru.minzdrav.therapist.core.designsystem.theme.BlueLavender
import ru.minzdrav.therapist.core.designsystem.theme.RedTheme
import ru.minzdrav.therapist.core.designsystem.theme.WhiteBackground

@Destination
@Composable
fun NoteScreen(
    resultNavigator: ResultBackNavigator<String>,
    title: String,
    placeholder: String
) {
    var text by remember { mutableStateOf("") }
    var fontSize by remember { mutableIntStateOf(16) }

    Scaffold(
        topBar = {
            NoteTopBar(title = title, onBack = resultNavigator::navigateBack)
        },
        containerColor = BlueLavender,
        bottomBar = {
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(RedTheme))
        },
    ) { padding ->
        BoxWithConstraints(
            Modifier
                .fillMaxSize()
                .padding(padding)) {
            ScreenDependentNoteContent(
                text = text,
                onValueChange = { text = it },
                fontSizeDp = fontSize,
                placeholder = placeholder.ifEmpty { stringResource(R.string.type_something) },
            )
        }
    }
}

@Composable
private fun BoxWithConstraintsScope.ScreenDependentNoteContent(
    text: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    fontSizeDp: Int
) {
    val constraints = this

    Column(
        modifier = Modifier
            .align(Alignment.TopStart)
            .fillMaxSize()
    ) {
        if (constraints.maxHeight > 200.dp) {
            Spacer(Modifier.height(AppTheme.dimen.largeSpace))
        }
        EditableText(
            value = text,
            onValueChange = onValueChange,
            style = AppTheme.typography.body.copy(
                // Independent font size
                fontSize = with(LocalDensity.current) { fontSizeDp.dp.toSp() }
            ),
            backgroundColor = WhiteBackground,
            placeholder = placeholder,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        if (constraints.maxHeight > 200.dp) {
            Spacer(Modifier.height(AppTheme.dimen.largeSpace))
        }
    }
}

@Composable
private fun NoteTopBar(
    title: String,
    onBack: () -> Unit
) {
    AppTopBar(
        title = title,
        navigationAction = {
            AppTopBarAction(iconResId = AppIcons.Back, onClick = onBack)
        },
    )
}