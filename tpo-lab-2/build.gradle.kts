plugins {
    id("java")
}

group = "com.amoguspro"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:4.0.0")
    testImplementation("org.mockito:mockito-junit-jupiter:4.0.0")
    implementation("ch.obermuhlner:big-math:2.3.2")
}

tasks.test {
    useJUnitPlatform()
}