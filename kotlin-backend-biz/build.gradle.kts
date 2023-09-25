plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))

    implementation(project(":kotlin-backend-common"))
    implementation(project(":kotlin-backend-stubs"))
}