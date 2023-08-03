@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("app.android.feature")
}

android {
    namespace = "ru.minzdrav.therapist.feature.note"
}

dependencies {
    implementation(libs.androidx.constraintlayout)
}