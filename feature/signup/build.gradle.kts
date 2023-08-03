plugins {
    id("app.android.feature")
}

android {
    namespace = "ru.minzdrav.therapist.feature.signup"
}

dependencies {
    implementation(project(":auth"))
}