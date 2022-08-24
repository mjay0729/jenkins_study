package com.lineplus.featurestore.global.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("v1-definition")
                .pathsToMatch("/**")
                .build();
    }
    @Bean
    public OpenAPI springShopOpenAPI() {
        Server server = new Server();
        server.setUrl("http://lab.bigle.ai:9000/api");
        return new OpenAPI()
                .info(new Info().title("FeatureStore API")
                        .description("FeatureStore 프로젝트 API 명세서입니다.")
                        .version("v0.0.1"))
                .servers(List.of(server));
    }
}