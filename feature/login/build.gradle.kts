plugins {
    id("app.android.feature")
}

android {
    namespace = "ru.minzdrav.therapist.feature.login"
}

dependencies {
    implementation(project(":auth"))
}