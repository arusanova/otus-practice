plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":kotlin-backend-api-v1-jackson"))
    implementation(project(":kotlin-backend-common"))

    testImplementation(kotlin("test-junit"))
}