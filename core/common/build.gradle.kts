plugins {
    id("app.android.library")
    id("app.android.hilt")
}

android {
    namespace = "ru.minzdrav.therapist.core.common"
}

dependencies {
    implementation(libs.kotlinx.datetime)
}