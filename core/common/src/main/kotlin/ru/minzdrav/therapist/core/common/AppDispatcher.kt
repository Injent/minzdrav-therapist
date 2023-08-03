package ru.minzdrav.therapist.core.common

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val dispatcher: AppDispatcher)

enum class AppDispatcher {
    IO
}