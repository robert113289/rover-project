plugins {
    id("java")
    id("application")
}

group = "com.rover"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.rover.Main")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:4.+")
    testImplementation("org.mockito:mockito-junit-jupiter:4.0.0")
    testImplementation("org.assertj:assertj-core:3.+")
}

tasks.test {
    useJUnitPlatform()
}