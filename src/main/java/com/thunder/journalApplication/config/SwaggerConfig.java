
package com.thunder.journalApplication.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.bson.types.ObjectId;
import org.springdoc.core.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

//    static {
//        SpringDocUtils.getConfig()
//                .addResponseTypeToIgnore(ObjectId.class);
//    }

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Journal Application APIs")
                                .version("1.0")
                                .description("by thunder tech")
                )

                .servers(Arrays.asList(
                        new Server()
                                .url("http://localhost:8080")
                                .description("local"),
                        new Server()
                                .url("http://localhost:8081")
                                .description("local")
                ))

                .tags(Arrays.asList(
                        new Tag().name("Public APIs")
                ))

                // JWT Authentication Configuration
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        SECURITY_SCHEME_NAME,
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                                .description("Enter your JWT token")
                                )
                )

                // Apply JWT authentication globally
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(SECURITY_SCHEME_NAME)
                );
    }
}
