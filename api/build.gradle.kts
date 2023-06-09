plugins {}

version = "0.0.1"

dependencies {

    implementation(project(":domain"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.6")

    // Swagger 의존성 추가
    implementation("io.springfox:springfox-boot-starter:3.0.0")

    // Jackson 의존성 추가
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.+")

    // test 를 적용하기 위한 mockk 의존성 추가
    testImplementation("io.mockk:mockk:1.12.0")
    runtimeOnly("com.h2database:h2")
    
    // AOP 의존성 추가
    implementation("org.springframework.boot:spring-boot-starter-aop")

}
