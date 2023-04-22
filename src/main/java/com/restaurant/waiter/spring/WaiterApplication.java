package com.restaurant.waiter.spring;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SecurityScheme(
		type = SecuritySchemeType.OAUTH2,
		name = "oauth2",
		description = "KeyCloak restaurant",
		flows = @OAuthFlows(
				implicit = @OAuthFlow(authorizationUrl = "https://yokudlela.drhealth.cloud/auth/realms/yokudlela/protocol/openid-connect/auth"
						+ "?client_id=account"
						+ "&redirect_uri=http://localhost:8080/swagger-ui/oauth2-redirect.html"
						+ "&response_type=code"
						+ "&scope=openid")
		)
)

@SecurityScheme(
		type = SecuritySchemeType.APIKEY,
		name = "apikey",
		paramName = "Authorization",
		description = "KeyCloak restaurant",
		in = SecuritySchemeIn.HEADER)


@SecurityScheme(
		type = SecuritySchemeType.OPENIDCONNECT,
		name = "openid",
		description = "KeyCloak restaurant",
		openIdConnectUrl = "https://yokudlela.drhealth.cloud/auth/realms/yokudlela/.well-known/openid-configuration"
)

@OpenAPIDefinition(
		servers = {
				@Server(url = "http://localhost:8080", description = "local dev")})

@Configuration
@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.restaurant.waiter")
@EnableJpaRepositories("com.restaurant.waiter.store")
@EntityScan("com.restaurant.waiter.datamodel")
@SpringBootApplication
public class WaiterApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaiterApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry){
				registry.addMapping("/**");
			}
		};
	}
}
