package com.kdnadev.proyectofinal_santiagocabrera.controller;

import org.springframework.web.bind.annotation.RestController;

import com.kdnadev.proyectofinal_santiagocabrera.common.response.GenericResponse;
import com.kdnadev.proyectofinal_santiagocabrera.model.Adopcion;
import com.kdnadev.proyectofinal_santiagocabrera.service.AdopcionService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/adopciones")
public class AdopcionController {

    private AdopcionService adopcionService;

    public AdopcionController (AdopcionService adopcionService){
        this.adopcionService = adopcionService;
    }

    @PostMapping("/")
    public ResponseEntity<GenericResponse<Adopcion>> create(@RequestBody Adopcion adopcion) {
        try {
            Adopcion adopcionCreada = adopcionService.create(adopcion);
            return ResponseEntity.ok(new GenericResponse<>(adopcionCreada));
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new GenericResponse<>(null, "Error al asignar adopcion", e.getMessage()));
        }
    }

}
