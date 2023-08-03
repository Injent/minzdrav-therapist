package ru.minzdrav.therapist.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.datetime.Instant
import ru.minzdrav.therapist.core.database.TherapistDatabase.Companion.TABLE_THERAPIST_CALLS
import ru.minzdrav.therapist.core.database.util.CallStatusConverter
import ru.minzdrav.therapist.core.database.util.PatientConverter
import ru.minzdrav.therapist.core.model.CallStatus
import ru.minzdrav.therapist.core.model.TherapistCall
import ru.minzdrav.therapist.core.model.patient.Patient

@TypeConverters(PatientConverter::class, CallStatusConverter::class)
@Entity(tableName = TABLE_THERAPIST_CALLS, indices = [Index(value = ["id"], unique = true)])
data class TherapistCallEntity(
    @PrimaryKey var id: Long = 0,
    val patient: Patient,
    val complaints: String,
    @ColumnInfo(name = "registration_date") val registrationDate: Instant,
    val status: CallStatus
) {
    fun asExternalModel() = TherapistCall(
        id = id,
        patient = patient,
        complaints = complaints,
        registrationDate = registrationDate,
        status = status
    )
}
