import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform") version "1.3.41"
}

repositories {
    mavenCentral()
}

kotlin {

    jvm {
        withJava()
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                api("com.squareup:kotlinpoet:1.3.0")
            }
        }
    }

    // Determine host preset.
    val hostOs = System.getProperty("os.name")

    // Create target for the host platform.
    val hostTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        hostOs.startsWith("Windows") -> mingwX64("native")
        else -> throw GradleException("Host OS '$hostOs' is not supported in Kotlin/Native $project.")
    }

    hostTarget.apply {
        val main by compilations.getting
        val interop by main.cinterops.creating
        
        binaries {
            sharedLib(listOf(RELEASE)) {
                compilation = main
            }
        }
    }

}


tasks.withType<Wrapper> {
    gradleVersion = "5.5.1"
    distributionType = Wrapper.DistributionType.ALL
}

