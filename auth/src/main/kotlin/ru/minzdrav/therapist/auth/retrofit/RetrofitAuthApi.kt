package ru.minzdrav.therapist.auth.retrofit

import retrofit2.http.Body
import retrofit2.http.POST
import ru.minzdrav.therapist.auth.model.AuthRequest
import ru.minzdrav.therapist.auth.model.AuthResponse

/**
 * Service for authentication and receiving authorization token
 */
interface RetrofitAuthApi {

    @POST("signup")
    suspend fun signUp(@Body request: AuthRequest)

    @POST("signin")
    suspend fun signIn(@Body request: AuthRequest): AuthResponse
}