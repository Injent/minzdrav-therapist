pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

rootProject.name = "therapist"
include(":app")
include(":lint")
include(":core:common")
include(":core:storage")
include(":core:model")
include(":core:designsystem")
include(":feature:login")
include(":auth")
include(":benchmark")
include(":feature:signup")
include(":core:network")
include(":core:data")
include(":core:testing")
include(":feature:home")
include(":feature:call-forms")
include(":core:database")
include(":core:domain")
include(":core:security")
include(":sync:work")
include(":feature:documents")
include(":core:ui")
//include(":update")
//include(":feature:update")
include(":feature:note")
