
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import ru.minzdrav.therapist.configureFlavors
import ru.minzdrav.therapist.configureKotlin

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
        }

        extensions.configure<ApplicationExtension> {
            configureKotlin(this)
            defaultConfig.targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
            configureFlavors(this)
        }
    }
}