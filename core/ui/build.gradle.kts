plugins {
    id("app.android.library")
    id("app.android.library.compose")
}

android {
    namespace = "ru.minzdrav.therapist.core.ui"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))

    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)
    api(libs.androidx.compose.runtime)

    debugApi(libs.androidx.compose.ui.tooling)

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.collections.immutable)

    androidTestImplementation(project(":core:testing"))
}