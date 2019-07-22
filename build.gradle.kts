
plugins {
    kotlin("multiplatform") version "1.3.41"
}

repositories {
    mavenCentral()
}

// Determine host preset.
val hostOs = System.getProperty("os.name")

kotlin {

    // Add JVM Target (for Kotlin class generation)
    jvm {
        withJava()
    }
    
    sourceSets {
        val jvmMain by getting {
            dependencies {
                api(kotlin("reflect"))
                api("com.squareup:kotlinpoet:1.3.0")
                api("com.google.code.gson:gson:2.8.5")
            }
        }
    }


    // Create target for the host platform.
    val hostTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        hostOs.startsWith("Windows") -> mingwX64("native")
        else -> null
    }

    // Configure Host Target (For C-Interop & Entry Hook code)
    if (hostTarget != null) {
        hostTarget.apply {
            val main by compilations.getting
            val interop by main.cinterops.creating

            binaries {
                sharedLib(listOf(RELEASE)) {
                    compilation = main
                }
            }
        }
    } else {
        throw Exception("Host OS '$hostOs' is not supported in Kotlin/Native $project.")
    }

}

task("moveSharedLib") {
    group = "kotdot"
    
    val dynLibName = when {
        hostOs.startsWith("Windows") -> "godot_kotlin.dll"
        else -> throw Exception("Can't move shared libs for other OS's yet.")
    }
    val libFile = File("build/bin/native/releaseShared", dynLibName)

    val godotLocation = File(ext.properties["godotLocation"].toString(), dynLibName)

    // Delete file if it exists
    if (godotLocation.exists()) {
        godotLocation.delete()
    }
    
    libFile.copyTo(godotLocation)

    //libFile.copyTo(godotLocation)
}


tasks.withType<Wrapper> {
    gradleVersion = "5.5.1"
    distributionType = Wrapper.DistributionType.ALL
}

