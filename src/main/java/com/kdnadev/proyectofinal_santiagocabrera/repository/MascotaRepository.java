package com.kdnadev.proyectofinal_santiagocabrera.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdnadev.proyectofinal_santiagocabrera.model.Mascota;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByTipoMascotaId(Long tipoMascotaId);
}
