val backendUrl = extra["backend_url"].toString()

plugins {
    id("app.android.library")
    id("app.android.hilt")
    id("app.android.serialization")
}

android {
    namespace = "ru.minzdrav.therapist.core.network"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "BACKEND_URL", "\"$backendUrl\"")

        consumerProguardFiles("consumer-proguard-rules.pro")
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okHttp.logging)

    testImplementation(libs.github.faker)
}