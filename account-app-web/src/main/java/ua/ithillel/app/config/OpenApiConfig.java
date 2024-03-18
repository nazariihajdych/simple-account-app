package ua.ithillel.app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Account App",
                        email = "nazar@hillel.com",
                        url = "htts://google.com"
                ),
                description = "OpenApi documentation for Account App",
                title = "AccountAppDocs, v1",
                version = "1.0",
                license = @License,
                termsOfService = "Terms of Service"
        ),
        servers = {
                @Server(
                        description = "DEV",
                        url = "localhost:8099"
                ),
                @Server(
                        description = "TEST",
                        url = "localhost:8099"
                )
        }
)
public class OpenApiConfig {
}
