package ru.minzdrav.therapist.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import ru.minzdrav.therapist.core.model.TherapistCall
import ru.minzdrav.therapist.core.model.forms.UiSchema
import ru.minzdrav.therapist.core.network.BuildConfig.BACKEND_URL
import ru.minzdrav.therapist.network.TherapistNetworkDataSource
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitTherapistNetworkApi {
    // Empty body
}

@Singleton
class RetrofitTherapistNetwork @Inject constructor(
    networkJson: Json
) : TherapistNetworkDataSource {
    private val networkApi = Retrofit.Builder()
        .baseUrl(BACKEND_URL)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create(RetrofitTherapistNetworkApi::class.java)

    override suspend fun getTherapistsCalls(): List<TherapistCall> {
        TODO("Not yet implemented")
    }

    override suspend fun getUiSchema(id: String): UiSchema {
        TODO("Not yet implemented")
    }
}

