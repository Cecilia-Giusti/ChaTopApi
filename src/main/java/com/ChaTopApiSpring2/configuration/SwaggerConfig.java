package com.ChaTopApiSpring2.configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.ant;
import static springfox.documentation.builders.PathSelectors.any;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    List<String> authPaths = Arrays.asList("/auth/me", "/message", "/rentals", "/rentals/*", "/user/*");


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ChaTopApiSpring2.controllers"))
                .paths(any())
                .build()
                .securitySchemes(List.of(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    private ApiKey apiKey() {
        return new ApiKey("Bearer", "Authorization", "header");
    }

    private SecurityReference defaultAuth() {
        return new SecurityReference("Bearer", new AuthorizationScope[]{new AuthorizationScope("global", "accessEverything")});
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(defaultAuth()))
                .forPaths(path -> authPaths.stream().anyMatch(antPath -> ant(path).test(antPath)))
                .build();
    }


}
