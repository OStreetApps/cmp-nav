val koinVersion = "3.4.3"

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Library for Compose Multiplatform navigation with Koin"
        homepage = "Link to the GitHub Repository"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.foundation)
                implementation("io.insert-koin:koin-core:$koinVersion")
            }
        }
    }
}

android {
    namespace = "com.ostreet.cmp.koin.navigation"
    compileSdk = 33
    defaultConfig {
        minSdk = 26
    }
}