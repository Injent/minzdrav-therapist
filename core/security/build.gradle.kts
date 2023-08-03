plugins {
    id("app.android.library")
    id("app.android.hilt")
}

android {
    namespace = "ru.minzdrav.therapist.core.security"
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.tink)

    implementation(libs.androidx.security.crypto)
}