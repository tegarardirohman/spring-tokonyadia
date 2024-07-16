package com.enigmacamp.tokonyadia;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SecurityScheme(
		name = "Authorization",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)
@SpringBootApplication
public class TokonyadiaApplication {

	public static void main(String[] args) {
		System.out.println("Tokonyadia Application Started");

		SpringApplication.run(TokonyadiaApplication.class, args);
	}


}
