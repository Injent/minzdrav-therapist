plugins {
    id("app.android.feature")
}

android {
    namespace = "ru.minzdrav.therapist.feature.update"
}

dependencies {
    implementation(project(":update"))

    implementation(libs.accompanist.permissions)
}