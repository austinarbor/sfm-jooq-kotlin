plugins {
    kotlin("jvm") version "1.8.20"
    `java-library`
    `maven-publish`
}

group = "dev.aga"
version = "0.0.1"

repositories {
    mavenCentral()
}

configurations {
    configurations.testImplementation.get().apply {
        extendsFrom(configurations.compileOnly.get())
    }
}

// this is ugly, but until https://github.com/dependabot/dependabot-core/issues/1164
// is resolved, we need to either duplicate the version strings or use something
// like this
val junitVersion by extra("5.9.2")
val sfmVersion by extra("8.2.3")
val jooqVersion by extra("3.18.2")
val assertJVersion by extra("3.24.2")

dependencies {
    implementation(kotlin("reflect"))

    compileOnly("org.simpleflatmapper:sfm-jooq:$sfmVersion")
    compileOnly("org.jooq:jooq-kotlin:$jooqVersion")

    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
}

kotlin {
    jvmToolchain(17)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group as String
            artifactId = project.name
            version = project.version as String

            from(components["java"])
        }
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
}
