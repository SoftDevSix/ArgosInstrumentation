plugins {
    java
    `java-library`
	 `jacoco`
    alias(libs.plugins.springboot) apply true
    alias(libs.plugins.dependency.management) apply true
    alias(libs.plugins.shadow) apply true
    alias(libs.plugins.spotless) apply true
	alias(libs.plugins.sonarqube) apply true
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

    developmentOnly(libs.springboot.devtools)

    testImplementation(libs.springboot.starter.test)
    testRuntimeOnly(libs.junit.launcher)

	compileOnly (libs.lombok)
	
	annotationProcessor (libs.lombok)
}

tasks.test {
    testLogging {
        showStandardStreams = true
    }

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

spotless {
    java {
        googleJavaFormat().aosp()
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
    }
}