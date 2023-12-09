plugins {
    id("java")
    kotlin("plugin.lombok") version "1.9.10"
    id("io.freefair.lombok") version "8.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    annotationProcessor ("org.projectlombok:lombok")
    implementation ("com.google.code.gson:gson:2.8.8")
}

tasks.test {
    useJUnitPlatform()
}