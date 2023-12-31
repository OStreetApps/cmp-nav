plugins {
    //trick: for the same plugin versions in all sub-modules
    kotlin("multiplatform").apply(false)
    id("com.android.application").version("8.1.1").apply(false)
    id("com.android.library") version "8.1.1" apply false
    id("org.jetbrains.compose").apply(false)
    id("maven-publish")
}

buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }


}

allprojects {

    group = "io.github.ostreetapps"
    version = "1.0"

}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
