import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project

class FeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("app.android.library")
            apply("app.android.library.compose")
            apply("app.android.compose.destinations")
            apply("app.android.hilt")
        }

        dependencies {
            implementation(project(":core:model"))
            implementation(project(":core:designsystem"))
            implementation(project(":core:ui"))
            implementation(project(":core:common"))
            implementation(project(":core:data"))
            implementation(project(":core:domain"))

            testImplementation(kotlin("test"))
            testImplementation(project(":core:testing"))
            androidTestImplementation(kotlin("test"))
            androidTestImplementation(project(":core:testing"))

            implementation(libs["androidx.lifecycle.runtimeCompose"])
            implementation(libs["androidx.lifecycle.viewModelCompose"])
            implementation(libs["androidx.hilt.navigation.compose"])

            implementation(libs["kotlinx.coroutines.android"])
        }
    }
}