plugins {
    kotlin("jvm") version "1.8.20"
    `java-library`
}

group = "dev.aga"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))

    compileOnly("org.simpleflatmapper:sfm-jooq:8.2.3")
    compileOnly("org.jooq:jooq-kotlin:3.18.2")

    testImplementation("org.simpleflatmapper:sfm-jooq:8.2.3")

    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.24.2")
}

kotlin {
    jvmToolchain(17)
}

tasks {
    test {
        useJUnitPlatform()
    }
}
