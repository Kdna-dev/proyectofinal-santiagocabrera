package com.kdnadev.proyectofinal_santiagocabrera.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kdnadev.proyectofinal_santiagocabrera.common.response.AdopcionResponse;
import com.kdnadev.proyectofinal_santiagocabrera.common.response.GenericResponse;
import com.kdnadev.proyectofinal_santiagocabrera.model.Adopcion;
import com.kdnadev.proyectofinal_santiagocabrera.service.AdopcionService;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN', 'CLIENTE')")
    public ResponseEntity<AdopcionResponse> create(@RequestBody Adopcion adopcion) {
        Adopcion adopcionCreada = adopcionService.create(adopcion);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(adopcionCreada.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(new AdopcionResponse(adopcionCreada));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    public ResponseEntity<GenericResponse<Void>> delete(@PathVariable Long id){
        adopcionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
