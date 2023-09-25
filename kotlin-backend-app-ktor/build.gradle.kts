import org.jetbrains.kotlin.util.suffixIfNot

val ktorVersion: String by project
val logbackVersion: String by project

plugins {
    id("io.ktor.plugin")
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version


// ex: Converts to "io.ktor:ktor-ktor-server-netty:2.0.1" with only ktor("netty")
fun ktor(module: String, prefix: String = "server-", version: String? = this@Build_gradle.ktorVersion): Any =
    "io.ktor:ktor-${prefix.suffixIfNot("-")}$module:$version"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    implementation(kotlin("stdlib-common"))

    // ktor
    implementation(ktor("core"))
    implementation(ktor("netty"))
    implementation(ktor("auto-head-response"))
    implementation(ktor("caching-headers"))
    implementation(ktor("content-negotiation"))
    implementation(ktor("default-headers"))
    implementation(ktor("cors"))
    implementation(ktor("auto-head-response"))
    implementation(ktor("locations"))
    implementation(ktor("call-logging"))
    implementation(ktor("jackson", "serialization"))

    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // projects
    implementation(project(":kotlin-backend-api-v1-jackson"))
    implementation(project(":kotlin-backend-common"))
    implementation(project(":kotlin-backend-mappers-v1"))
    implementation(project(":kotlin-backend-biz"))

    // test
    implementation(kotlin("test-junit"))
    implementation(ktor("test-host"))
    implementation(ktor("content-negotiation", prefix = "client-"))
}