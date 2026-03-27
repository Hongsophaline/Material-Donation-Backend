package Material.Donation.APP.demo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("Gifting Engine API")
                        .version("1.0")
                        .description("API documentation for Material Donation App"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    // This bean prints the link to your terminal on startup
    @Bean
    public ApplicationRunner repositoryInitializer(Environment env) {
        return args -> {
            String port = env.getProperty("server.port", "8080");
            System.out.println("\n----------------------------------------------------------");
            System.out.println(" Gifting Engine API is running!");
            System.out.println(" Swagger UI: http://localhost:" + port + "/swagger-ui/index.html");
            System.out.println("----------------------------------------------------------\n");
        };
    }
}