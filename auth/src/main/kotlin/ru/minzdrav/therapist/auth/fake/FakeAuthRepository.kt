package ru.minzdrav.therapist.auth.fake

import kotlinx.datetime.Clock
import ru.minzdrav.therapist.auth.data.AuthRepository
import ru.minzdrav.therapist.auth.result.AuthResult
import ru.minzdrav.therapist.core.model.UserAuthorization
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.days

@Singleton
class FakeAuthRepository @Inject constructor() : AuthRepository {
    private val fakeResult = AuthResult.Authorized(
        UserAuthorization(
            accessToken = "fake_access_token",
            expiresIn = Clock.System.now().plus((30L).days),
            refreshToken = "fake_refresh_token",
            userId = 1
        )
    )

    override suspend fun signUp(email: String, password: String): AuthResult = fakeResult

    override suspend fun signIn(email: String, password: String): AuthResult = fakeResult
}