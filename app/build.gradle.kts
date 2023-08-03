@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("app.android.application")
    id("app.android.application.compose")
    id("app.android.compose.destinations")
    id("app.android.hilt")
}

android {
    namespace = "ru.minzdrav.therapist"

    defaultConfig {
        applicationId = "ru.minzdrav.therapist"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        val release by getting {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
        create("benchmark") {
            initWith(release)
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles("benchmark-rules.pro")
            matchingFallbacks.add("release")
            isDebuggable = false
            applicationIdSuffix = ".benchmark"
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
    implementation(project(":core:storage"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))
    implementation(project(":core:network"))
    implementation(project(":feature:login"))
    implementation(project(":feature:signup"))
    implementation(project(":feature:call-forms"))
    implementation(project(":feature:home"))
    implementation(project(":feature:documents"))
    implementation(project(":feature:note"))
    implementation(project(":sync:work"))

    implementation(libs.accompanist.navigation)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.accompanist.systemUiController)
    implementation(libs.androidx.window.manager)
}