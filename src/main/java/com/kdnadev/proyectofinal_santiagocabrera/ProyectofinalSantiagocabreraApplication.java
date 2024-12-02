package com.kdnadev.proyectofinal_santiagocabrera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

import java.io.File;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
	info = @Info(
		title = "Adopcion de mascotas API",
		version = "1.0",
		description = "API para gestionar la adopcion de mascotas de una veterinaria, y sus usuarios",
		contact = @Contact(
			name = "Kdna.dev | Santiago Cabrera",
			email = "kdna.dev@gmail.com",
			url = "https://github.com/Kdna-dev"
		)
	)
)
@SpringBootApplication
public class ProyectofinalSantiagocabreraApplication {

	@PostConstruct
	public void init() {
		// Crear directorio de logs si no existe
		File logDir = new File("logs/archived");
		if (!logDir.exists()) {
			logDir.mkdirs();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(ProyectofinalSantiagocabreraApplication.class, args);
	}

}
