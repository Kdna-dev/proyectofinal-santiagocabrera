package com.kdnadev.proyectofinal_santiagocabrera.service;

import java.util.List;
import java.util.Optional;
import java.sql.Date;
import org.springframework.stereotype.Service;

import com.kdnadev.proyectofinal_santiagocabrera.model.Mascota;
import com.kdnadev.proyectofinal_santiagocabrera.repository.MascotaRepository;

@Service
public class MascotaService {
    private MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository){
        this.mascotaRepository = mascotaRepository;
    }

    public List<Mascota> getAll(){
        return mascotaRepository.findAll();
    }

    public Optional<Mascota> getById(Long id){
        return mascotaRepository.findById(id);
    }

    public void deleteById(Long id){
        mascotaRepository.deleteById(id);
    }

    /**
     * Registra una nueva mascota,
     * el atributo disponibleParaAdopcion estara por defecto en false
     * @param mascota: Datos de la nueva mascota
     * @return type Mascota
     */
    public Mascota create(Mascota mascota){
        Date fechaActual = new Date(System.currentTimeMillis());
        mascota.setFechaCreacion(fechaActual);
        mascota.setDisponibleParaAdopcion(false);

        return mascotaRepository.save(mascota);
    }

    /**
     * Actualiza la mascota correspondiente con los datos nuevos,
     * en caso de no encontrar un registro para dicho identificador
     * generara una nueva mascota.
     * @param id: Identificador de la mascota a actualizar
     * @param actualizacionMascota: Datos nuevos de la mascota
     * @return type Mascota
     */
    public Mascota update(Long id, Mascota actualizacionMascota){
        return mascotaRepository.findById(id)
            .map(m -> {
                m.setNombre(actualizacionMascota.getNombre());
                m.setTipoMascota(actualizacionMascota.getTipoMascota());
                m.setEdad(actualizacionMascota.getEdad());

                Date fechaActual = new Date(System.currentTimeMillis());
                m.setFechaActualizacion(fechaActual);

                return mascotaRepository.save(m);

            }).orElseGet(() -> {
                return create(actualizacionMascota);
            });
    }

    public Mascota setDisponibleParaAdopcion(Long id, boolean disponible){
        mascotaRepository.setDisponibleParaAdopcionById(id, disponible);
        return mascotaRepository.getReferenceById(id);
    }
}
