package com.kdnadev.proyectofinal_santiagocabrera.controller;

import com.kdnadev.proyectofinal_santiagocabrera.common.response.GenericResponse;
import com.kdnadev.proyectofinal_santiagocabrera.dto.mascota.MascotaCreateDTO;
import com.kdnadev.proyectofinal_santiagocabrera.dto.mascota.MascotaMapper;
import com.kdnadev.proyectofinal_santiagocabrera.dto.mascota.MascotaResponse;
import com.kdnadev.proyectofinal_santiagocabrera.dto.mascota.MascotaUpdateDTO;
import com.kdnadev.proyectofinal_santiagocabrera.dto.tipo_mascota.TipoMascotaCreateDTO;
import com.kdnadev.proyectofinal_santiagocabrera.dto.tipo_mascota.TipoMascotaMapper;
import com.kdnadev.proyectofinal_santiagocabrera.dto.tipo_mascota.TipoMascotaResponse;
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
    private MascotaMapper mascotaMapper;
    private TipoMascotaMapper tipoMascotaMapper;

    public MascotaController(MascotaService mascotaService, MascotaMapper mascotaMapper, TipoMascotaMapper tipoMascotaMapper) {
        this.mascotaService = mascotaService;
        this.mascotaMapper = mascotaMapper;
        this.tipoMascotaMapper = tipoMascotaMapper;
    }

    @Operation(summary = "Obtener todas las mascotass", description = "Retorna una lista de todas las mascotas", responses = {
        @ApiResponse(responseCode = "200", description = "Lista total de mascotas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MascotaResponse.class)))
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<MascotaResponse> getAll() {
        List<Mascota> mascotas = mascotaService.getAll();
        return ResponseEntity.ok(new MascotaResponse(mascotaMapper.toDTO(mascotas)));
    }

    @Operation(summary = "Obtener mascota", description = "Retorna la informacion de la mascota solicitada por id en un array de 1 elemento", responses = {
            @ApiResponse(responseCode = "200", description = "Mascota encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MascotaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Mascota no encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class)))
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<MascotaResponse> getById(@PathVariable Long id) {
        Mascota mascota = mascotaService.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe la mascota con id: " + id));

        return ResponseEntity.ok(new MascotaResponse(mascotaMapper.toDTO(mascota)));
    }

    //TODO Implementar getAllDisponibleAdopcion con acceso de cliente
    //TODO Implementar getByIdDisponibleAdopcion con acceso de cliente

    @PostMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<MascotaResponse> create(@RequestBody MascotaCreateDTO mascota) {
        Mascota mascotaCreada = mascotaService.create(mascota);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(mascotaCreada.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(new MascotaResponse(mascotaMapper.toDTO(mascotaCreada)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<MascotaResponse> update(@PathVariable Long id, @RequestBody MascotaUpdateDTO mascota) {
        Mascota mascotaActualizada = mascotaService.update(id, mascota);
        return ResponseEntity.ok(new MascotaResponse(mascotaMapper.toDTO(mascotaActualizada)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        mascotaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("setDisponible/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<MascotaResponse> setDisponibleParaAdopcion(@PathVariable Long id,
            @RequestParam boolean disponible) {
        Mascota mascotaActualizada = mascotaService.setDisponibleParaAdopcion(id, disponible);
        return ResponseEntity.ok(new MascotaResponse(mascotaMapper.toDTO(mascotaActualizada)));
    }

    @PostMapping("/tipoMascota")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TipoMascotaResponse> createTipoMascota(@RequestBody TipoMascotaCreateDTO tipo) {
        TipoMascota tipoCreado = mascotaService.createTipoMascota(tipo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new TipoMascotaResponse(tipoMascotaMapper.toDTO(tipoCreado)));
    }

    @GetMapping("/tipoMascota")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<TipoMascotaResponse> getAllTipoMascota() {
        List<TipoMascota> tipoMascotas = mascotaService.getAllTipoMascota();
        return ResponseEntity.ok(new TipoMascotaResponse(tipoMascotaMapper.toDTO(tipoMascotas)));
    }
}
