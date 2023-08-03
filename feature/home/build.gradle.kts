plugins {
    id("app.android.feature")
}

android {
    namespace = "ru.minzdrav.therapist.feature.home"
}

dependencies {
    implementation(project(":auth"))

    implementation(libs.kotlinx.collections.immutable)
}