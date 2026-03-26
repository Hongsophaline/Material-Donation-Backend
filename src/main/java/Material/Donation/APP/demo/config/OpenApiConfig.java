package Material.Donation.APP.demo.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String port = event.getApplicationContext().getEnvironment().getProperty("server.port", "8080");
        String host = "http://localhost";
        String path = "/swagger-ui/index.html";
        
        System.out.println("\n----------------------------------------------------------");
        System.out.println("Gifting Engine API is running!");
        System.out.println("Access Swagger UI at: " + host + ":" + port + path);
        System.out.println("----------------------------------------------------------\n");
    }
}