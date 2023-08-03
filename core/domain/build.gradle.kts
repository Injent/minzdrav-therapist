plugins {
    id("app.android.library")
    id("app.android.hilt")
}

android {
    namespace = "ru.minzdrav.therapist.core.domain"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data"))

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    testImplementation(project(":core:testing"))
}