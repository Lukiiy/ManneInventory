plugins {
    java
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