package ru.minzdrav.therapist.network.fake

import android.content.Context
import android.content.res.AssetManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import ru.minzdrav.therapist.core.common.AppDispatcher.IO
import ru.minzdrav.therapist.core.common.Dispatcher
import ru.minzdrav.therapist.core.model.TherapistCall
import ru.minzdrav.therapist.core.model.forms.UiSchema
import ru.minzdrav.therapist.network.TherapistNetworkDataSource
import javax.inject.Inject

@OptIn(ExperimentalSerializationApi::class)
class FakeTherapistNetworkDataSource @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    @ApplicationContext context: Context
) : TherapistNetworkDataSource {

    private val assets: AssetManager = context.assets

    override suspend fun getTherapistsCalls(): List<TherapistCall> = withContext(ioDispatcher) {
        networkJson.decodeFromStream(
            assets.open("therapist_calls.json")
        )
    }

    override suspend fun getUiSchema(id: String): UiSchema = withContext(ioDispatcher) {
        networkJson.decodeFromStream(
            assets.open("$id.json")
        )
    }
}