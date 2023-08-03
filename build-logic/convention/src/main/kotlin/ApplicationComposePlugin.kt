import com.android.build.api.dsl.ApplicationExtension
import ru.minzdrav.therapist.configureCompose
import ru.minzdrav.therapist.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class ApplicationComposePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.android.application")
        configureKotlin(extensions.getByType<ApplicationExtension>())
        configureCompose(extensions.getByType<ApplicationExtension>())
    }
}