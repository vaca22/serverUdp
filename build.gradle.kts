plugins {
    kotlin("jvm") version "1.5.10"
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    implementation("org.apache.logging.log4j:log4j-api: 2.14.1")
    implementation("org.apache.logging.log4j:log4j-core:2.14.1")
    implementation("io.netty:netty-all:4.1.44.Final")
    // https://mvnrepository.com/artifact/org.json/json
    implementation("org.json:json:20210307")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}