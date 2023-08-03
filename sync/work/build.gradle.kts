plugins {
    id("app.android.library")
    id("app.android.hilt")
}

android {
    namespace = "ru.minzdrav.therapist.sync"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:storage"))

    implementation(libs.androidx.startup)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.tracing.ktx)
    implementation(libs.hilt.ext.work)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.work)

    kapt(libs.hilt.ext.compiler)

    androidTestImplementation(project(":core:testing"))
    androidTestImplementation(libs.androidx.work.testing)
}