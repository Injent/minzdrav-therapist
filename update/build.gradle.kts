plugins {
    id("app.android.library")
    id("app.android.hilt")
}

android {
    namespace = "ru.minzdrav.therapist.update"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:data"))

    implementation(libs.okHttp3)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.tracing.ktx)
    implementation(libs.hilt.ext.work)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.work)

    kapt(libs.hilt.ext.compiler)

    androidTestImplementation(project(":core:testing"))
    androidTestImplementation(libs.androidx.work.testing)
}