package com.project.apature.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
/*
API 관리 - Swagger 작성
 */
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("com.project.apature")
                .pathsToMatch("/**")
                .packagesToScan("com.project.apature.controller")
                .build();
    }
}
