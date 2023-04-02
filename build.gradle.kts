plugins {
    kotlin("jvm") version "1.8.20"
    `java-library`
    `maven-publish`
    signing
    id("org.jetbrains.kotlinx.kover") version "0.7.0-Alpha"
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

koverReport {
    xml {
        onCheck = true
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            groupId = project.group as String
            artifactId = project.name
            version = project.version as String

            pom {
                name.set("${project.group}:${project.name}")
                description.set("Extension library for sfm-jooq to add better kotlin support")
                url.set("https://github.com/austinarbor/sfm-jooq-kotlin")

                licenses {
                    license {
                        name.set("The MIT License")
                        url.set("http://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("austinarbor")
                        name.set("Austin G. Arbor")
                        email.set("aarbor989@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:https://github.com/austinarbor/sfm-jooq-kotlin.git")
                    developerConnection.set("scm:git:https://github.com/austinarbor/sfm-jooq-kotlin.git")
                    url.set("https://github.com/austinarbor/sfm-jooq-kotlin/tree/main")
                }
            }
        }
    }
    repositories {
        maven {
            name = "OSSRH"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("OSSH_USERNAME")
                password = System.getenv("OSSH_PASSWORD")
            }
        }
    }
}

signing {
    isRequired = gradle.taskGraph.allTasks.any { it is PublishToMavenRepository }
    val keyId = System.getenv("OSSH_GPG_KEY_ID")
    val signingKey = System.getenv("OSSH_GPG_SIGNING_KEY")
    val signingPassword = System.getenv("OSSH_GPG_PASSPHRASE")
    useInMemoryPgpKeys(keyId, signingKey, signingPassword)
    sign(publishing.publications)
}
