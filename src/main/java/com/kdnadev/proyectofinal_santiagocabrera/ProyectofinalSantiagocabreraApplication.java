package com.kdnadev.proyectofinal_santiagocabrera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

import java.io.File;

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
