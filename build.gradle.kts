plugins {
    id("java")
    id("idea")
    id("com.gradleup.shadow") version "8.3.5"
}

group = "fr.pandonia"
version = "1.0-SNAPSHOT"
description = "The implementation of the hub server"

repositories {
    mavenCentral()
}

dependencies {
    // Minestom
    implementation("net.minestom:minestom-snapshots:1_21_4-4da5831880")

    // Logging
    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation("org.slf4j:slf4j-simple:2.0.13")
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "fr.pandonia.hub.Main"
        }
    }

    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        mergeServiceFiles()
        archiveClassifier.set("")
    }
}
