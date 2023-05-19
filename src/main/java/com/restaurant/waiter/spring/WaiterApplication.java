package com.restaurant.waiter.spring;

import com.restaurant.waiter.utils.request.RequestBean;
import com.restaurant.waiter.utils.request.UserNameInjectInterceptor;
import com.restaurant.waiter.utils.validation.ValidationRestDataExceptionHandler;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SecurityScheme(
		type = SecuritySchemeType.OAUTH2,
		name = "oauth2",
		description = "KeyCloak restaurant",
		flows = @OAuthFlows(
				implicit = @OAuthFlow(authorizationUrl = "http://restaurant:6080/auth/realms/restaurant/protocol/openid-connect/auth"
						+ "?client_id=account"
						+ "&redirect_uri=http://restaurant:8080/swagger-ui/oauth2-redirect.html"
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
		openIdConnectUrl = "http://restaurant:6080/auth/realms/restaurant/.well-known/openid-configuration"
)

@OpenAPIDefinition(
		servers = {
				@Server(url = "http://restaurant:8080", description = "local dev")})

@Configuration
@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.restaurant.waiter")
@EnableJpaRepositories("com.restaurant.waiter.store")
@EntityScan("com.restaurant.waiter.datamodel")
@SpringBootApplication
@Import(ValidationRestDataExceptionHandler.class)
public class WaiterApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaiterApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer(){
		return new WebMvcConfigurer() {
			@Autowired
			UserNameInjectInterceptor customInterceptor;

			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(customInterceptor);
			}

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**");
			}
		};
	}

	@Bean("requestScopedBean")
	@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public RequestBean requestBean() {
		MDC.put("application", "2");
		MDC.put("host", "3");
		return new RequestBean();
	}

	@Bean("applicationContextProvider")
	public ApplicationContextProvider createApplicationContextProvider() {

		return new ApplicationContextProvider();
	}
}
