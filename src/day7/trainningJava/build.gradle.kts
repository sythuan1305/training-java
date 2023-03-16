plugins {
    java
    id("org.springframework.boot") version "3.0.3"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "com.beetech"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
    implementation("org.thymeleaf:thymeleaf:3.1.1.RELEASE")
    implementation("org.thymeleaf:thymeleaf-spring5:3.0.15.RELEASE")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:2.4.1")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("mysql:mysql-connector-java:8.0.32")
//    implementation("com.liferay:javax.xml.bind:2.3.0.LIFERAY-PATCHED-2")
    implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.security:spring-security-test")
    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

//tasks.test {
////	outputs.dir(snippetsDir)
//}

//tasks.asciidoctor {
//	inputs.dir(snippetsDir)
//	dependsOn(test)
//}
