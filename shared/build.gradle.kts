import org.jetbrains.compose.desktop.application.tasks.AbstractNativeMacApplicationPackageAppDirTask
import org.jetbrains.kotlin.gradle.plugin.mpp.AbstractExecutable
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBinary
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink
import org.jetbrains.kotlin.library.impl.KotlinLibraryLayoutImpl
import java.io.File
import java.io.FileFilter
import org.jetbrains.kotlin.konan.file.File as KonanFile


val koinVersion = "3.4.3"
val icerockVersion = "0.16.1"

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java).all {
        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
            export("dev.icerock.moko:mvvm-core:$icerockVersion")
        }
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
        extraSpecAttributes["resources"] = "['src/commonMain/resources/**', 'src/iosMain/resources/**']"
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.insert-koin:koin-core:$koinVersion")
                implementation(project(":koin-navigation"))
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)

            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.7.2")
            }
            dependsOn(commonMain)
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.ostreet.cmp.cmpnav"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
}

dependencies {
    commonMainApi("dev.icerock.moko:mvvm-core:$icerockVersion")
    commonMainApi("dev.icerock.moko:mvvm-compose:$icerockVersion")
    commonMainApi("dev.icerock.moko:mvvm-flow:$icerockVersion")
    commonMainApi("dev.icerock.moko:mvvm-flow-compose:$icerockVersion")
}

tasks.withType<KotlinNativeLink>()
    .matching { linkTask -> linkTask.binary is AbstractExecutable }
    .configureEach {
        val task: KotlinNativeLink = this

        doLast {
            val binary: NativeBinary = task.binary
            val outputDir: File = task.outputFile.get().parentFile
            task.libraries
                .filter { library -> library.extension == "klib" }
                .filter(File::exists)
                .forEach { inputFile ->
                    val klibKonan = KonanFile(inputFile.path)
                    val klib = KotlinLibraryLayoutImpl(
                        klib = klibKonan,
                        component = "default"
                    )
                    val layout = klib.extractingToTemp

                    // extracting bundles
                    layout
                        .resourcesDir
                        .absolutePath
                        .let(::File)
                        .listFiles(FileFilter { it.extension == "bundle" })
                        // copying bundles to app
                        ?.forEach { bundleFile ->
                            logger.info("${bundleFile.absolutePath} copying to $outputDir")
                            bundleFile.copyRecursively(
                                target = File(outputDir, bundleFile.name),
                                overwrite = true
                            )
                        }
                }
        }
    }

tasks.withType<AbstractNativeMacApplicationPackageAppDirTask> {
    val task: AbstractNativeMacApplicationPackageAppDirTask = this

    doLast {
        val execFile: File = task.executable.get().asFile
        val execDir: File = execFile.parentFile
        val destDir: File = task.destinationDir.asFile.get()
        val bundleID: String = task.bundleID.get()

        val outputDir = File(destDir, "$bundleID.app/Contents/Resources")
        outputDir.mkdirs()

        execDir.listFiles().orEmpty()
            .filter { it.extension == "bundle" }
            .forEach { bundleFile ->
                logger.info("${bundleFile.absolutePath} copying to $outputDir")
                bundleFile.copyRecursively(
                    target = File(outputDir, bundleFile.name),
                    overwrite = true
                )
            }
    }
}