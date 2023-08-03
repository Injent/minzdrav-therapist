package ru.minzdrav.therapist.core.data.repository.fake

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.minzdrav.therapist.core.data.model.asEntity
import ru.minzdrav.therapist.core.data.repository.TherapistCallRepository
import ru.minzdrav.therapist.core.data.util.Synchronizer
import ru.minzdrav.therapist.core.database.dao.TherapistCallDao
import ru.minzdrav.therapist.core.database.model.TherapistCallEntity
import ru.minzdrav.therapist.core.model.TherapistCall
import ru.minzdrav.therapist.network.TherapistNetworkDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeTherapistCallRepository @Inject constructor(
    private val networkDataSource: TherapistNetworkDataSource,
    private val therapistCallDao: TherapistCallDao
) : TherapistCallRepository {

    override fun getLatestTherapistCalls(): Flow<List<TherapistCall>> {
        return therapistCallDao.getLatestTherapistCalls().map {
            it.map(TherapistCallEntity::asExternalModel)
        }
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        therapistCallDao.clear()
        val entities = networkDataSource.getTherapistsCalls()
            .map(TherapistCall::asEntity)
        therapistCallDao.insert(entities)
        with(synchronizer.getDataVersions()) {
            synchronizer.updateDataVersions(copy(calls + 1))
        }
        return true
    }
}