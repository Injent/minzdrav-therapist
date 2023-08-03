import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "ru.minzdrav.therapist.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("applicationCompose") {
            id = "app.android.application.compose"
            implementationClass = "ApplicationComposePlugin"
        }
        register("application") {
            id = "app.android.application"
            implementationClass = "ApplicationPlugin"
        }
        register("libraryCompose") {
            id = "app.android.library.compose"
            implementationClass = "LibraryComposePlugin"
        }
        register("library") {
            id = "app.android.library"
            implementationClass = "LibraryPlugin"
        }
        register("feature") {
            id = "app.android.feature"
            implementationClass = "FeaturePlugin"
        }
        register("hilt") {
            id = "app.android.hilt"
            implementationClass = "HiltPlugin"
        }
        register("kotlinSerialization") {
            id = "app.android.serialization"
            implementationClass = "SerializationPlugin"
        }
        register("composeDestinations") {
            id = "app.android.compose.destinations"
            implementationClass = "ComposeDestinationsPlugin"
        }
        register("test") {
            id = "app.android.test"
            implementationClass = "AndroidTestPlugin"
        }
    }
}