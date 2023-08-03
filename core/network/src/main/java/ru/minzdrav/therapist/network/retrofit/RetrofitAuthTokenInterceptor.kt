package ru.minzdrav.therapist.network.retrofit

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import ru.minzdrav.therapist.core.network.BuildConfig.BACKEND_URL
import ru.minzdrav.therapist.network.model.AuthToken
import ru.minzdrav.therapist.network.model.RefreshTokenRequest
import ru.minzdrav.therapist.network.model.RefreshTokenResponse
import javax.inject.Inject

/**
 * OkHttp interceptor which adds auth token to network requests
 */
class RetrofitAuthTokenInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val authToken = tokenManager.provideToken() ?: run {
            return@runBlocking chain.proceed(chain.request())
        }

        if (tokenManager.isExpired()) {
            refreshToken(chain, authToken.refreshToken)
        }

        val request =  chain.request().newBuilder()
            .header(AUTH_TOKEN_HEADER, "Bearer ${authToken.accessToken}")
            .build()

        chain.proceed(request)
    }

    private suspend fun refreshToken(chain: Interceptor.Chain, refreshToken: String) {
        val json = Json
        val refreshTokenRequest = RefreshTokenRequest("refresh_token", refreshToken)
        val requestData = json.encodeToString(RefreshTokenRequest.serializer(), refreshTokenRequest)
            .toRequestBody("application/json".toMediaType())
        val request = chain.request().newBuilder()
            .post(requestData)
            .url("$BACKEND_URL/refreshToken")
            .build()

        val proceed = chain.proceed(request)
        val response = proceed.body

        if (response != null) {
            val refreshTokenResponse: RefreshTokenResponse = json.decodeFromString(response.string())

            val updatedAuthToken = with(refreshTokenResponse) {
                AuthToken(accessToken, expiresIn, refreshToken)
            }
            tokenManager.saveToken(updatedAuthToken)
        }

        proceed.close()
    }

    private companion object {
        const val AUTH_TOKEN_HEADER = "Authorization"
    }
}
