package ru.minzdrav.therapist.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.minzdrav.therapist.core.database.TherapistDatabase.Companion.TABLE_THERAPIST_CALLS
import ru.minzdrav.therapist.core.database.model.TherapistCallEntity

@Dao
interface TherapistCallDao {

    @Query("SELECT * from $TABLE_THERAPIST_CALLS")
    fun getLatestTherapistCalls(): Flow<List<TherapistCallEntity>>

    @Insert(onConflict = IGNORE)
    suspend fun insert(therapistCalls: List<TherapistCallEntity>): List<Long>

    @Query("DELETE from $TABLE_THERAPIST_CALLS")
    suspend fun clear()
}