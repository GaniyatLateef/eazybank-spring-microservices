package com.eazybank.accounts;

import com.eazybank.accounts.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableFeignClients
@EnableConfigurationProperties(AccountsContactInfoDto.class)
@OpenAPIDefinition(
	info =@Info(
			title = "Accounts Microservice REST API Documentation",
			description = "EasyBankAccounts Microservice REST API Documentation",
		    version = "v1",
		    contact =@Contact(
				name = "Ganiyat",
				email = "ganiyat1425@gmail.com",
				url = "https://github.com/ganiyat1425"
			),
		    license = @License(
			   name = "Apache 2.0",
			   url = "https://www.apache.org/licenses/LICENSE-2.0"
		)
	),
	externalDocs = @ExternalDocumentation(
		description = "EasyBankAccounts Documentation",
		url = "https://eazybank.com/swagger-ui.html"
	)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);

	}

}
