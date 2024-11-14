package com.kdnadev.proyectofinal_santiagocabrera.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import com.kdnadev.proyectofinal_santiagocabrera.model.Mascota;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByTipoMascotaId(Long tipoMascotaId);

    @Modifying
    @Transactional
    @Query("UPDATE Mascota m SET m.disponibleParaAdopcion = :disponible WHERE m.id = :id")
    Mascota setDisponibleParaAdopcionById(@Param("id") Long id, @Param("disponible") boolean disponible);

}
