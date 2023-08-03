plugins {
    id("app.android.library")
    id("app.android.hilt")
    id("app.android.serialization")
    id("com.google.devtools.ksp")
}

android {
    namespace = "ru.minzdrav.therapist.core.database"
}

ksp {
    arg("room.schemaLocation", File(projectDir, "schemas").path)
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:security"))

    implementation(libs.cipher)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.datetime)

    androidTestImplementation(project(":core:testing"))
}