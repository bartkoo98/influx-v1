package com.bartkoo98.influxv1;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "InfluxAPP REST APIs",
				description = "Spring Rest APIs documentation",
				version = "v1.0"
		), externalDocs = @ExternalDocumentation(
				description = "Github repository for project",
				url = "https://github.com/bartkoo98/influx-v1"
		)
)
public class InfluxV1Application {

	public static void main(String[] args) {
		SpringApplication.run(InfluxV1Application.class, args);
	}

}
