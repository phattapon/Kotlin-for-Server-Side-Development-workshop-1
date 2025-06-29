// build.gradle.kts

val kotlin_version: String by project
val logback_version: String by project
val ktor_version = "2.3.11" // Your Ktor version
val kotlinx_serialization_version = "1.6.0" // Or the latest stable version

plugins {
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.2.0" // Your Ktor plugin version (make sure this is compatible with ktor_version)
    kotlin("plugin.serialization") version "2.1.10" // Must match kotlin("jvm") version
}

group = "com.example"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.openfolder:kotlin-asyncapi-ktor:3.1.1")

    // Ktor Core and Engine
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")

    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Ktor Features
    // Ktor-server-core is already included with -jvm, you usually don't need both unless specific reason.
    // Let's keep it minimal if -jvm covers it.
    // If you explicitly need io.ktor:ktor-server-core without -jvm (e.g., for common module),
    // you would add it. For JVM server, -jvm is usually enough.
    // Removing the duplicate 'ktor-server-core' if 'ktor-server-core-jvm' is used.
    // implementation("io.ktor:ktor-server-core:$ktor_version") // <-- REMOVE THIS LINE IF YOU HAVE -JVM

    implementation("io.ktor:ktor-server-config-yaml:$ktor_version")

    // !!! IMPORTANT !!! REMOVE THIS LINE: ktor-server-routing is not needed for Ktor 2.x
    // implementation("io.ktor:ktor-server-routing:$ktor_version") // <-- REMOVE THIS LINE

    // Ktor Content Negotiation and Kotlinx JSON
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    // Kotlinx Serialization runtime
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinx_serialization_version")

    // Test Dependencies
    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}