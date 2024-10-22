package com.kdnadev.proyectofinal_santiagocabrera.controller;

import com.kdnadev.proyectofinal_santiagocabrera.model.Mascota;
import com.kdnadev.proyectofinal_santiagocabrera.service.MascotaService;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {
    private MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService){
        this.mascotaService = mascotaService;
    }

    @GetMapping
    public List<Mascota> getAll() {
        return mascotaService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> getById(@PathVariable Long id){
        Optional<Mascota> mascota = mascotaService.getById(id);
        if(!mascota.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public Mascota create(@RequestBody Mascota mascota) {
        return mascotaService.create(mascota);
    }

    @PutMapping("/{id}")
    public Mascota update(@PathVariable Long id, @RequestBody Mascota mascota) {
        return mascotaService.update(id, mascota);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        mascotaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("setDisponible/{id}")
    public Mascota setDisponibleParaAdopcion(@PathVariable Long id, @RequestParam boolean disponible) {
        return mascotaService.setDisponibleParaAdopcion(id, disponible);
    }
}
