package com.eazyBank.cards;

import com.eazyBank.cards.dto.CardsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {CardsContactInfoDto.class})
@OpenAPIDefinition(
	info = @Info(
		title = "Cards microservice REST API Documentation",
		version = "v1",
		description = "API for Cards",
		contact = @Contact(
			name = "Ganiyat",
			email = "ganiyat1425@gmail.com",
			url = "https://github.com/ganiyat1425"
	    ),
		license = @License(
			name = "Apache 2.0",
			url = "https://www.apache.org/licenses/LICENSE-2.0")
	),
	externalDocs = @ExternalDocumentation(
		description = "EazyBank Cards microservice REST API Documentation",
		url = "https://github.com/ganiyat1425/swagger-ui.html"
	)
		)

public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}

}
