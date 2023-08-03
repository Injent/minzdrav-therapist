import com.android.build.gradle.TestExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import ru.minzdrav.therapist.configureKotlin

class AndroidTestPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.test")
            apply("org.jetbrains.kotlin.android")
        }

        extensions.configure<TestExtension> {
            configureKotlin(this)
            defaultConfig.targetSdk = 31
        }
    }
}