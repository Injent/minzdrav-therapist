plugins {
    id("app.android.library")
    id("app.android.serialization")
}

android {
    namespace = "ru.minzdrav.therapist.core.model"
}

dependencies {
    api(libs.kotlinx.datetime)
}