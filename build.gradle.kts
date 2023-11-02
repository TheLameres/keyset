plugins {
    java
    id("org.springframework.boot") version "2.7.17"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "thelameres"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["blazePersistenceVersion"] = "1.6.9"
extra["springBootVersion"] = "2.7.17"
extra["springdocVersion"] = "1.7.0"
extra["springCloudVersion"] = "2022.0.4"
extra["testcontainersVersion"] = "1.19.1"

dependencyManagement {
    imports {
        mavenBom("com.blazebit:blaze-persistence-bom:${property("blazePersistenceVersion")}")
        mavenBom("org.springframework.boot:spring-boot-dependencies:${property("springBootVersion")}")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.blazebit:blaze-persistence-core-api")
    implementation("com.blazebit:blaze-persistence-core-impl")
    implementation("com.blazebit:blaze-persistence-entity-view-api")
    implementation("com.blazebit:blaze-persistence-entity-view-impl")
    implementation("com.blazebit:blaze-persistence-integration-entity-view-spring")
    implementation("com.blazebit:blaze-persistence-integration-spring-data-webmvc")
    implementation("com.blazebit:blaze-persistence-integration-spring-data-2.7")
    implementation("com.blazebit:blaze-persistence-integration-hibernate-5.6")
    implementation("org.liquibase:liquibase-core")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-ui:${property("springdocVersion")}")

    compileOnly("org.projectlombok:lombok")
    compileOnly("org.hibernate:hibernate-jpamodelgen")
    compileOnly("com.blazebit:blaze-persistence-entity-view-processor")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")

    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.hibernate:hibernate-jpamodelgen")
    annotationProcessor("com.blazebit:blaze-persistence-entity-view-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:junit-jupiter")


}

tasks.withType<Test> {
    useJUnitPlatform()
}
