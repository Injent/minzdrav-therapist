package ru.minzdrav.therapist.core.database.util

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import ru.minzdrav.therapist.core.model.CallStatus
import ru.minzdrav.therapist.core.model.patient.Patient

class InstantConverter {
    @TypeConverter
    fun longToInstant(value: Long?): Instant? =
        value?.let(Instant::fromEpochMilliseconds)

    @TypeConverter
    fun instantToLong(instant: Instant?): Long? =
        instant?.toEpochMilliseconds()
}

class PatientConverter {
    @TypeConverter
    fun patientToJson(patient: Patient?): String? =
        patient?.let { Json.encodeToString(Patient.serializer(), it) }

    @TypeConverter
    fun jsonToPatient(json: String?): Patient? =
        json?.let { Json.decodeFromString(Patient.serializer(), json) }
}

class CallStatusConverter {
    @TypeConverter
    fun callStatusToString(callStatus: CallStatus?): String? =
        callStatus?.toString()

    @TypeConverter
    fun stringToCallStatus(string: String?): CallStatus? =
        string?.let { CallStatus.valueOf(string) }
}