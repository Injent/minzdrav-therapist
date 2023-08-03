package ru.minzdrav.therapist.auth.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.HttpException
import retrofit2.Retrofit
import ru.minzdrav.therapist.auth.BuildConfig.BACKEND_URL
import ru.minzdrav.therapist.auth.data.AuthRepository
import ru.minzdrav.therapist.auth.model.AuthRequest
import ru.minzdrav.therapist.auth.result.AuthResult
import ru.minzdrav.therapist.auth.result.AuthResult.Authorized
import ru.minzdrav.therapist.auth.result.AuthResult.NetworkConnectionError
import ru.minzdrav.therapist.auth.result.AuthResult.ServerError
import ru.minzdrav.therapist.auth.result.AuthResult.Unauthorized
import ru.minzdrav.therapist.auth.result.AuthResult.UnknownError
import ru.minzdrav.therapist.core.common.AppDispatcher.IO
import ru.minzdrav.therapist.core.common.Dispatcher
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val UNAUTHORIZED = 401
private const val INTERNAL_SERVER_ERROR = 500

@Singleton
class RetrofitAuthRepository @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : AuthRepository {

    private val api = Retrofit.Builder()
        .baseUrl(BACKEND_URL)
        .addConverterFactory(
            @OptIn(ExperimentalSerializationApi::class)
            Json.asConverterFactory("application/json".toMediaTypeOrNull()!!)
        )
        .build()
        .create(RetrofitAuthApi::class.java)

    override suspend fun signUp(email: String, password: String) = handleAuthExceptions {
        val request = AuthRequest(email, password)
        withContext(ioDispatcher) {
            api.signUp(request)
            Authorized(api.signIn(request).asUserAuthorization())
        }
    }

    override suspend fun signIn(email: String, password: String) = handleAuthExceptions {
        withContext(ioDispatcher) {
            Authorized(api.signIn(AuthRequest(email, password)).asUserAuthorization())
        }
    }

    private suspend fun handleAuthExceptions(run: suspend () -> AuthResult): AuthResult = try {
        run()
    } catch (e: HttpException) {
        when (e.code()) {
            UNAUTHORIZED -> Unauthorized
            INTERNAL_SERVER_ERROR -> ServerError(e)
            else -> UnknownError(e)
        }
    } catch (e: IOException) {
        NetworkConnectionError
    } catch (e: Exception) {
        UnknownError(e)
    }
}