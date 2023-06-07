package com.zerobase.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

@Configuration
class SwaggerConfig {
    // SWAGGER 접속 URL -> http://localhost:8080/swagger-ui/index.html
    @Bean
    fun api(): Docket {
        val basePackage = "com.zerobase.api"
        return Docket(DocumentationType.OAS_30)
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.basePackage(basePackage))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
    }

    private fun apiInfo(): ApiInfo {
        val title = "대출 심사 프로젝트"
        val description = "대출 심사 프로젝트 백엔드 API"
        val version = "1.0"

        return ApiInfoBuilder()
            .title(title)
            .description(description)
            .version(version)
            .build()
    }
}