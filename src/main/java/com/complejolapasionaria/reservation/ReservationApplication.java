package com.complejolapasionaria.reservation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info =@Info(
				title = "Reservation Manager",
				version = "v1.0",
				contact = @Contact(name = "Juan Manuel López", email = "juanem@hotmail.com"),
				license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"),
				//termsOfService = "${tos.uri}",
				description = "# Reservation Manager by juanem"

		)
		/*,
		servers = @Server(
				url = "",
				description = ""
		)*/
)
@SecurityScheme(
		name = "Bearer Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "Bearer"
)
public class ReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationApplication.class, args);
	}

}
