plugins {
    id("app.android.library")
    id("app.android.library.compose")
    id("app.android.hilt")
}

android {
    namespace = "ru.minzdrav.therapist.core.testing"
}

dependencies {
    api(libs.androidx.compose.ui.test)
    api(libs.androidx.test.core)
    api(libs.androidx.test.espresso.core)
    api(libs.androidx.test.rules)
    api(libs.androidx.test.runner)
    api(libs.hilt.android.testing)
    api(libs.junit4)
    api(libs.kotlinx.coroutines.test)
    api(libs.mockito)
    api(libs.mockito.kotlin)
    debugApi(libs.androidx.compose.ui.testManifest)

    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(libs.kotlinx.datetime)
}