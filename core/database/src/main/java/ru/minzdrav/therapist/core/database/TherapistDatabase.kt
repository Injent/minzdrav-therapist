package ru.minzdrav.therapist.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.minzdrav.therapist.core.database.dao.TherapistCallDao
import ru.minzdrav.therapist.core.database.model.TherapistCallEntity
import ru.minzdrav.therapist.core.database.util.InstantConverter

@Database(
    entities = [
        TherapistCallEntity::class
    ],
    version = 1
)
@TypeConverters(
    InstantConverter::class
)
abstract class TherapistDatabase : RoomDatabase() {
    abstract fun therapistCallDao(): TherapistCallDao

    companion object {
        const val TABLE_THERAPIST_CALLS = "therapist_calls"
    }
}