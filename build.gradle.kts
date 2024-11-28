plugins {
    `java-library`
    `jacoco`
    alias(libs.plugins.springboot) apply true
    alias(libs.plugins.dependency.management) apply true
    alias(libs.plugins.shadow) apply true
    alias(libs.plugins.spotless) apply true
    alias(libs.plugins.sonarqube) apply true
    alias(libs.plugins.lombok) apply true
}

group = "edu.usb.argosinstrumentation"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.springboot.starter)
    implementation(libs.asm)
    implementation(libs.logback)
    implementation(libs.jackson.databind)
    implementation(libs.springboot.devtools)
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    testImplementation(libs.springboot.starter.test)
    testRuntimeOnly(libs.junit.launcher)
}

tasks.test {
    testLogging {
        events("failed", "skipped")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
    useJUnitPlatform()
}

tasks.withType<Jar> {
    manifest {
        attributes(
            "Premain-Class" to "org.coverage.Agent"
        )
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = true
        csv.required = false
        html.required = true
    }
}

spotless {
    java {
        googleJavaFormat("1.24.0").aosp()
            .reflowLongStrings()
            .formatJavadoc(false)
            .reorderImports(false)
    }
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter(libs.versions.junit.jupiter.get())
        }
    }
}

sonar {
    val sonarProjectKey = System.getenv("SONAR_PROJECT_KEY") ?: ""
    val sonarHostUrl = System.getenv("SONAR_HOST_URL") ?: ""
    val sonarToken = System.getenv("SONAR_TOKEN") ?: ""
    properties {
        property("sonar.projectKey", sonarProjectKey)
        property("sonar.host.url", sonarHostUrl)
        property("sonar.token", sonarToken)
        property("sonar.qualitygate.wait", "true")
        property("sonar.ignore.cognitive.complexity", "MethodData.equals")
    }
}
// Shadow Plugin Configuration
tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveClassifier.set("all") // Genera un JAR completo con sufijo "-all"

    manifest {
        attributes(
            "Premain-Class" to "edu.usb.argosinstrumentation.agent.Agent", // Clase principal del agente
            "Main-Class" to "edu.usb.argosinstrumentation.agent.Agent"    // Clase main para ejecución
        )
    }

    from(sourceSets.main.get().output)

    mergeServiceFiles()
}


tasks.jar {
    enabled = false // Disables the standard jar task to avoid conflicts
}

tasks.bootJar {
    enabled = false // Ensures the fat jar is generated instead of the Spring Boot executable jar
}
