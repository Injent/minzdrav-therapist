package ru.minzdrav.therapist.core.data.repository.fake

import ru.minzdrav.therapist.core.data.repository.ServerDrivenUiRepository
import ru.minzdrav.therapist.core.model.forms.UiSchema
import ru.minzdrav.therapist.network.TherapistNetworkDataSource
import javax.inject.Inject

class FakeServerDrivenUiRepository @Inject constructor(
    private val networkDataSource: TherapistNetworkDataSource
) : ServerDrivenUiRepository {
    override suspend fun getUiSchema(id: String): UiSchema =
        networkDataSource.getUiSchema(id)
}