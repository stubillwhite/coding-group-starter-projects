val springBootVersion: String by project

plugins {
	java
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.1.3"
}

group = "starter"
version = "0.0.1"

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.1.4")
    implementation("javax.inject:javax.inject:1")
    implementation("javax.servlet:javax.servlet-api:4.0.0")

    // Utils
    implementation("com.google.guava:guava:29.0-jre")

	// Testing
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.4")
    testImplementation("io.rest-assured:rest-assured:5.3.2")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
