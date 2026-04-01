plugins {
    java
    id("com.gradleup.shadow") version "9.4.1"
}

group = "me.lukiiy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.9-R0.1-SNAPSHOT")
    implementation(files("lib/Barrel-1.0-SNAPSHOT.jar"))
}

tasks {
    shadowJar {
        mergeServiceFiles()
        archiveClassifier.set("")
        minimize()
    }

    build { dependsOn(shadowJar) }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))

    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21

    withSourcesJar()
}