package com.kdnadev.proyectofinal_santiagocabrera.controller;

import com.kdnadev.proyectofinal_santiagocabrera.common.response.GenericResponse;
import com.kdnadev.proyectofinal_santiagocabrera.common.response.MascotaResponse;
import com.kdnadev.proyectofinal_santiagocabrera.exception.ResourceNotFoundException;
import com.kdnadev.proyectofinal_santiagocabrera.model.Mascota;
import com.kdnadev.proyectofinal_santiagocabrera.model.TipoMascota;
import com.kdnadev.proyectofinal_santiagocabrera.service.MascotaService;

import java.net.URI;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/mascotas")
@Tag(name = "Mascotas", description = "Controlador para gestionar mascotas y tipos de mascotas")
public class MascotaController {
    private MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @Operation(summary = "Obtener todas las mascotass", description = "Retorna una lista de todas las mascotas")
    @GetMapping
    public ResponseEntity<MascotaResponse> getAll() {
        List<Mascota> mascotas = mascotaService.getAll();
        return ResponseEntity.ok(new MascotaResponse(mascotas));
    }

    @Operation(
        summary = "Obtener mascota",
        description = "Retorna la informacion de la mascota solicitada por id en un array de 1 elemento",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Mascota encontrada",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = MascotaResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Mascota no encontrada",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GenericResponse.class)
                )
            )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponse> getById(@PathVariable Long id) {
        Mascota mascota = mascotaService.getById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No existe la mascota con id: " + id));

        return ResponseEntity.ok(new MascotaResponse(mascota));
    }

    @PostMapping
    public ResponseEntity<MascotaResponse> create(@RequestBody Mascota mascota) {
        Mascota mascotaCreada = mascotaService.create(mascota);

        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(mascotaCreada.getId())
            .toUri();

        return ResponseEntity
            .created(location)
            .body(new MascotaResponse(mascotaCreada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaResponse> update(@PathVariable Long id, @RequestBody Mascota mascota) {
        Mascota mascotaActualizada = mascotaService.update(id, mascota);
        return ResponseEntity.ok(new MascotaResponse(mascotaActualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        mascotaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("setDisponible/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MascotaResponse> setDisponibleParaAdopcion(@PathVariable Long id, @RequestParam boolean disponible) {
        Mascota mascotaActualizada = mascotaService.setDisponibleParaAdopcion(id, disponible);
        return ResponseEntity.ok(new MascotaResponse(mascotaActualizada));
    }

    @PostMapping("/tipoMascota")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<TipoMascota>> create(@RequestBody String tipo) {
        TipoMascota tipoCreado = mascotaService.createTipoMascota(tipo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GenericResponse<>(tipoCreado));
    }
}
