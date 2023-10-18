rootProject.name = "marketplace-practice"

//include("m1l1-hello")
include("kotlin-backend-api-v1-jackson")
include("kotlin-backend-common")
include("kotlin-backend-mappers-v1")

include("kotlin-backend-app-ktor")
include("kotlin-backend-biz")
include("kotlin-backend-stubs")

include("kotlin-backend-app-kafka")

include("kotlin-backend-lib-cor")

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    val ktorVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        id("org.openapi.generator") version openapiVersion apply false
        id("io.ktor.plugin") version ktorVersion apply false
    }

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}
