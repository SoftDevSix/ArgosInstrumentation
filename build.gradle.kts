plugins {
    application
    `java-library`
    alias(libs.plugins.springboot.web) apply true
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
    implementation(libs.springboot.starter.web)
    implementation(libs.springboot.starter)
    implementation(libs.springdoc.openapi)
    developmentOnly(libs.springboot.devtools)
    testImplementation(libs.springboot.starter.test)
    testRuntimeOnly(libs.junit.launcher)
    implementation(libs.asm)
    implementation(libs.commons.math3)
    implementation(libs.guava)
	compileOnly (libs.lombok)
	annotationProcessor (libs.lombok)
}

application {
    mainClass.set("edu.usb.argosinstrumentation.ArgosInstrumentationApplication")
}

tasks.withType<Test> {
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