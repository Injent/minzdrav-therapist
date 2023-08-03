package ru.minzdrav.therapist.core.designsystem.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import ru.minzdrav.therapist.core.designsystem.theme.BlackText
import ru.minzdrav.therapist.core.designsystem.theme.Blue
import ru.minzdrav.therapist.core.designsystem.theme.BlueWave
import ru.minzdrav.therapist.core.designsystem.theme.TherapistTheme
import ru.minzdrav.therapist.core.designsystem.theme.WhiteText
import java.time.format.DateTimeFormatter
import java.util.Locale

@Preview
@Composable
fun A() {
    TherapistTheme {
        val calendarState by rememberCalendarState()

        DatePickerDialog(
            state = calendarState,
            onDateSelected = {

            }
        )
    }
}

@Composable
fun DatePickerDialog(
    state: CalendarState,
    onDateSelected: (LocalDate) -> Unit
) {
    Dialog(
        onDismissRequest = { state }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            contentPadding = PaddingValues(16.dp),
        ) {
            items(state.cells) { cell ->
                CalendarCell(
                    cell = cell,
                    onClick = {
                        onDateSelected(
                            with(state.startDate) {
                                LocalDate(
                                    year = year,
                                    month = month,
                                    dayOfMonth = cell.value.toIntOrNull() ?: dayOfMonth
                                )
                            }
                        )
                    },
                    modifier = Modifier.aspectRatio(1f)
                )
            }
        }
    }
}

@Composable
private fun CalendarCell(
    cell: CalendarCell,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cellColor by animateColorAsState(
        targetValue = when {
            cell.selected -> Blue
            cell.currentDay -> BlueWave
            else -> Color.Transparent
        },
    )

    val textColor by animateColorAsState(
        targetValue = if (cell.selected) WhiteText else BlackText
    )

    Box(
        modifier = modifier
            .background(color = cellColor, shape = CircleShape)
            .defaultMinSize(24.dp, 24.dp)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = cell.value,
            color = textColor,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
fun rememberCalendarState(
    selectedDate: LocalDate? = null,
    startDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
) = produceState(
    initialValue = CalendarState(startDate = startDate)
) {
    value = CalendarState(
        selectedDay = selectedDate?.dayOfMonth ?: 1,
        startDate = startDate,
        cells = getCalendarCells(startDate)
    )
}

@Immutable
data class CalendarState(
    val selectedDay: Int? = null,
    val startDate: LocalDate,
    val cells: List<CalendarCell> = emptyList(),
    val opened: Boolean = false
)

@Immutable
data class CalendarCell(
    val value: String,
    val selected: Boolean = false,
    val canBeSelected: Boolean = false,
    val currentDay: Boolean = false,
)

private fun getCalendarCells(startDate: LocalDate): List<CalendarCell> {
    // Getting the short names of the days of the week
    val weeksCells = localizedDayOfWeeks().map { dayOfWeek ->
        CalendarCell(value = dayOfWeek)
    }

    // Getting weeks that do not belong to the current month and replace their numbers with empty
    // cells
    val emptyCells = List(getFirstWeeksCountFromOtherMonth(startDate)) {
        CalendarCell(value = "")
    }

    val daysCells = (1..startDate.toJavaLocalDate().lengthOfMonth()).map { dayNumber ->
        CalendarCell(
            value = dayNumber.toString(),
            canBeSelected = true,
            selected = startDate.dayOfMonth == dayNumber,
            currentDay = startDate.dayOfMonth == dayNumber,
        )
    }

    return weeksCells + emptyCells + daysCells
}

private fun localizedDayOfWeeks(): List<String> {
    val formatter = DateTimeFormatter.ofPattern("EE", Locale.getDefault())

    return DayOfWeek.values().map { dayOfWeek ->
        formatter.format(dayOfWeek)
    }
}

private fun getFirstWeeksCountFromOtherMonth(startDate: LocalDate): Int {
    return DayOfWeek.values().size - startDate.dayOfWeek.value
}