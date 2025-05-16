package com.example.tenpoChallenge;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableAsync
@OpenAPIDefinition(
		info = @Info(
				title = "Tenpo Challenge Backend API",
				version = "1.0",
				description = "API para realizar cálculos con porcentajes dinámicos y recuperar el historial"
		)
)
public class TenpoChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TenpoChallengeApplication.class, args);
	}

}
