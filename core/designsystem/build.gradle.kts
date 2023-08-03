plugins {
    id("app.android.library")
    id("app.android.library.compose")
}

android {
    namespace = "ru.minzdrav.therapist.core.designsystem"
    lint {
        checkDependencies = true
    }
    defaultConfig {
        vectorDrawables {
            useSupportLibrary = true
        }
    }
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.androidx.core.ktx)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.material3)
    debugApi(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.kotlinx.datetime)
    implementation(libs.sheetsComposeDialogs.calendar)
    lintPublish(project(":lint"))
}
