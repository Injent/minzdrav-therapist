package ru.minzdrav.therapist.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.minzdrav.therapist.core.data.model.asEntity
import ru.minzdrav.therapist.core.data.util.Synchronizer
import ru.minzdrav.therapist.core.database.dao.TherapistCallDao
import ru.minzdrav.therapist.core.database.model.TherapistCallEntity
import ru.minzdrav.therapist.core.model.TherapistCall
import ru.minzdrav.therapist.core.storage.UserPreferencesDataSource
import ru.minzdrav.therapist.network.TherapistNetworkDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirstOfflineTherapistCallRepository @Inject constructor(
    private val network: TherapistNetworkDataSource,
    private val therapistCallDao: TherapistCallDao,
    private val userPreferencesDataSource: UserPreferencesDataSource
) : TherapistCallRepository {

    override fun getLatestTherapistCalls(): Flow<List<TherapistCall>> {
        return therapistCallDao.getLatestTherapistCalls().map {
            it.map(TherapistCallEntity::asExternalModel)
        }
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        val therapistCalls = network.getTherapistsCalls()
            .map(TherapistCall::asEntity)

        therapistCallDao.insert(therapistCalls)
        return true
    }

}