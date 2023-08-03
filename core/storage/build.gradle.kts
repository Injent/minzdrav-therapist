@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("app.android.library")
    id("app.android.hilt")
    id("app.android.serialization")
    alias(libs.plugins.protobuf)
}

android {
    defaultConfig {
        consumerProguardFiles("consumer-proguard-rules.pro")
    }
    namespace = "ru.minzdrav.therapist.core.storage"
}

// Setup protobuf configuration, generating lite Java and Kotlin classes
protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                val java by registering {
                    option("lite")
                }
                val kotlin by registering {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:security"))

    implementation(libs.tink)
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.protobuf.kotlin.lite)
}