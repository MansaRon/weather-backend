package co.za.demo;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SecurityScheme(name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "jwt")

@SecurityRequirement(name = "BearerAuth")
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
