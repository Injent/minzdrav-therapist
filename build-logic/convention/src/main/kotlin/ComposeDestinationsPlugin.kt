import com.android.build.api.variant.LibraryVariant
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class ComposeDestinationsPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.google.devtools.ksp")

        dependencies {
            implementation(libs["compose.destinations.animations"])
            ksp(libs["compose.destinations.ksp"])
        }

        extensions.configure<KspExtension> {
            arg("compose-destinations.mode", "destinations")
            arg("compose-destinations.moduleName", "${project.name}-module")
        }

        extensions.configure<KotlinAndroidProjectExtension> {
            objects.domainObjectSet(LibraryVariant::class.java).configureEach {
                sourceSets.getByName(name) {
                    kotlin.srcDir("build/generated/ksp/$name/kotlin")
                }
            }
        }
    }
}