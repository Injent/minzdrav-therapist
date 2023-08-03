plugins {
    kotlin("jvm")
    id("com.android.lint")
}

dependencies {
    compileOnly(libs.kotlin.stdlib)
    compileOnly(libs.lint.api)
}