package br.com.lifeundercontroll;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class LifeUnderControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(LifeUnderControlApplication.class, args);
	}

	@Bean
	public SecurityConfiguration security() {
		return new SecurityConfiguration("client-id", "client-secret", "realm", "APILUC", "token", ApiKeyVehicle.HEADER,
				"token", ",");
	}

	@Bean
	public Docket newsApi() {
		List<ApiKey> apiKey = new ArrayList<ApiKey>();
		apiKey.add(apiKey());

		return new Docket(DocumentationType.SWAGGER_2).groupName("LifeUnderControll").securitySchemes(apiKey)
				.apiInfo(apiInfo()).select().paths(regex("/api.*")).build();
	}

	private ApiKey apiKey() {
		return new ApiKey("token", "token", "header");
	}

	@Value("${version}")
	private String version;
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Spring REST API LUC")
				.description("LifeUnderControll: Your Financial Life in our hand").license("Apache License Version 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").version(version).build();
	}


}
