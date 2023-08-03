plugins {
    id("app.android.library")
    id("app.android.hilt")
}

android {
    namespace = "ru.minzdrav.therapist.core.data"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:storage"))
    implementation(project(":core:database"))
    
    implementation(libs.kotlinx.coroutines.android)
}