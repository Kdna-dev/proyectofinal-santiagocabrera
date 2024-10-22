package com.kdnadev.proyectofinal_santiagocabrera.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.kdnadev.proyectofinal_santiagocabrera.model.Mascota;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByTipoMascota(Long tipoMascota);

    @Modifying
    @Query("UPDATE mascota m SET m.disponibleParaAdopcion = :disponible WHERE m.id = :id")
    Mascota setDisponibleParaAdopcionById(Long id, boolean disponible);
}
