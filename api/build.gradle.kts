plugins {}

version = "0.0.1"

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.6")

    // Swagger 적용을 위한 의존성 추가
    implementation("io.springfox:springfox-boot-starter:3.0.0")

    implementation(project(":domain"))
}
