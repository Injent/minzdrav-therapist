package ru.minzdrav.therapist.auth.util

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Before
import org.junit.Test
import ru.minzdrav.therapist.auth.result.ValidationResult.Success
import ru.minzdrav.therapist.auth.result.ValidationResult.Failure

class CredentialsValidatorTest {

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
    }

    @Test
    fun validPassword_isValid() {
        assert(CredentialsValidator.validatePassword("Sample1#") is Success)
    }

    @Test
    fun shortPassword_isNotValid() {
        assert(CredentialsValidator.validatePassword("sampl#1") is Failure)
    }

    @Test
    fun passwordWithoutUppercaseLetters_isNotValid() {
        assert(CredentialsValidator.validatePassword("sample1#") is Failure)
    }

    @Test
    fun passwordWithoutLowercaseLetters_isNotValid() {
        assert(CredentialsValidator.validatePassword("SAMPLE1#") is Failure)
    }

    @Test
    fun passwordWithoutSpecialCharacter_isNotValid() {
        assert(CredentialsValidator.validatePassword("SAMPLE12") is Failure)
    }

    @Test
    fun passwordWithoutDigits_isNotValid() {
        assert(CredentialsValidator.validatePassword("Sample#$") is Failure)
    }
}