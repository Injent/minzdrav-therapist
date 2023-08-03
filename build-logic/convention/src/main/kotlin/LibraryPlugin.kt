
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import ru.minzdrav.therapist.configureFlavors
import ru.minzdrav.therapist.configureKotlin

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
        }

        extensions.configure<LibraryExtension> {
            configureKotlin(this)
            defaultConfig.targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
            configureFlavors(this)

            sourceSets.getByName("demo").assets.srcDirs(files("$projectDir/sampledata"))
        }

        configurations.configureEach {
            resolutionStrategy {
                force(libs.findLibrary("junit4").get())
            }
        }
        dependencies {
            androidTestImplementation(kotlin("test"))
            testImplementation(kotlin("test"))
        }
    }
}