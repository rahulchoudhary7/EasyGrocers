package com.easygrocers.gateway.config;


import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Primary
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API Gateway")
                        .description("API Gateway for microservices")
                        .version("1.0"));
    }

    @Bean
    public List<GroupedOpenApi> apis(RouteDefinitionLocator routeDefinitionLocator) {
        List<GroupedOpenApi> groups = new ArrayList<>();
        List<String> definitions = routeDefinitionLocator.getRouteDefinitions()
                .map(routeDefinition -> routeDefinition.getId())
                .collectList()
                .block();

        definitions.stream()
                .filter(definition -> definition.matches(".*-service"))
                .forEach(definition -> {
                    String name = definition.replaceAll("-service", "");
                    groups.add(GroupedOpenApi.builder()
                            .pathsToMatch("/api/v1/public/" + name + "/**", "/api/v1/public/" + name + "/api-docs/**", "/api/v1/public/" + name + "/swagger-ui/**")
                            .group(name)
                            .build());
                });

        return groups;
    }

}