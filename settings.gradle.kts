rootProject.name = "marketplace-practice"
include("m1l1-hello")

pluginManagement {
    val kotlinVersion: String by settings

    plugins {
        kotlin("jvm") version  kotlinVersion apply false
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
    }

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

