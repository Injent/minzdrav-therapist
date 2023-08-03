val backendUrl = extra["backend_url"].toString()

plugins {
    id("app.android.library")
    id("app.android.serialization")
    id("app.android.hilt")
}

android {
    namespace = "ru.minzdrav.therapist.auth"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "BACKEND_URL", "\"$backendUrl\"")
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:data"))

    implementation(libs.retrofit.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlin.serialization)

    implementation(project(":core:testing"))
}
