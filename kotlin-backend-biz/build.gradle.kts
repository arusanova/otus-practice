plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    val coroutinesVersion: String by project
    implementation(kotlin("stdlib"))

    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")

    implementation(project(":kotlin-backend-common"))
    implementation(project(":kotlin-backend-stubs"))
    implementation(project(":kotlin-backend-lib-cor"))


    testImplementation(kotlin("test-junit"))
}