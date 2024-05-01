package com.blog.jysblog.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SwaggerConfig {
    @Bean
    @Profile("!Prod")
    public OpenAPI openAPI() {
        String jwtSchemeName = "Bearer 토큰 입력";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

        Components components = new Components()
            .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                .name(jwtSchemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer")
                .bearerFormat("Bearer"));


        return new OpenAPI().info(
                new Info()
                    .title("Blog")
                    .description("Blog API")
                    .version("1.0.0")
            );
//            .addSecurityItem(securityRequirement)  //swagger 토큰검증 제외  추후 설정 필요
//            .components(components);

    }

}