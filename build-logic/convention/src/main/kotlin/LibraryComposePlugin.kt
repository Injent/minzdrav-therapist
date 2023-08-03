import com.android.build.gradle.LibraryExtension
import ru.minzdrav.therapist.configureCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class LibraryComposePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.android.library")
        configureCompose(extensions.getByType<LibraryExtension>())
    }
}