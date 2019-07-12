plugins {
    kotlin("multiplatform") version "1.3.21"
}

repositories {
    mavenCentral()
}

kotlin {
    macosX64("native") {
        val main by compilations.getting
        val interop by main.cinterops.creating

        binaries {
            executable()
        }
    }
}


tasks.withType<Wrapper> {
    gradleVersion = "5.5.1"
    distributionType = Wrapper.DistributionType.ALL
}