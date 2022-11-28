plugins {
    java
    id("org.springframework.boot") version ("2.4.0")
    id("io.spring.dependency-management") version ("1.0.10.RELEASE")
}

group = "com.xdesign"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.opencsv:opencsv:5.7.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}
