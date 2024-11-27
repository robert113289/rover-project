plugins {
    id("java")
    id("application")
}

group = "com.kirkum.rover"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.kirkum.rover.Main") // Replace with your main class
}

//tasks.register<JavaExec>("run") {
//    group = "application"
//    mainClass.set(application.mainClass)
//    classpath = sourceSets["main"].runtimeClasspath
//}

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