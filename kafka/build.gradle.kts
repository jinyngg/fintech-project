plugins {}

version = "0.0.1"

dependencies {
    // Kafka 의존성 추가
    implementation("org.springframework.kafka:spring-kafka:2.8.0")
    // Jackson 의존성 추가
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.+")
}
