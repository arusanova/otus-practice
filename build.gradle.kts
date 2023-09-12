plugins {
    kotlin("jvm")
}

allprojects {
    group = "ru.otus"
    version = "1.0-SNAPSHOT"

    repositories {
        google()
        mavenCentral()
        maven {url = uri("https://jitpack.io")}
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>{
        kotlinOptions.jvmTarget = "17"
    }
}