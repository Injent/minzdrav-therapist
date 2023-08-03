package ru.minzdrav.therapist.core.model.forms

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
sealed class ViewKind {

    abstract val id: String
    abstract val data: ViewData<*>

    @Serializable
    @SerialName("TEXTFIELD")
    data class TextField(
        override val id: String,
        @Transient override val data: ViewData<String> = ViewData(""),
        val label: String? = null,
        val placeholder: String = "",
        val singleLine: Boolean = false,
        val tip: String? = null,
        val inputType: InputType = InputType.TEXT,
        val autoCorrect: Boolean = true,
        val noteIsRequired: Boolean = false,
    ) : ViewKind() {
        enum class InputType {
            TEXT,
            NUMBER,
            DECIMAL
        }
    }

    @Serializable
    @SerialName("SPINNER")
    data class Spinner(
        override val id: String,
        @Transient override val data: ViewData<Int> = ViewData(0),
        val entries: List<String>,
    ) : ViewKind()

    @Serializable
    @SerialName("CHECKBOX")
    data class CheckBox(
        override val id: String,
        @Transient override val data: ViewData<Boolean> = ViewData(false),
        val text: String? = null
    ) : ViewKind()
}