rootProject.name = "marketplace-practice"

//include("m1l1-hello")
include("kotlin-backend-api-v1-jackson")
include("kotlin-backend-common")
include("kotlin-backend-mappers-v1")

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
    }

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}
