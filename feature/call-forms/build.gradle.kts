plugins {
    id("app.android.feature")
}

android {
    namespace = "ru.minzdrav.therapist.feature.callforms"
}

dependencies {
    implementation(libs.kotlinx.collections.immutable)
}