package com.kdnadev.proyectofinal_santiagocabrera.service;

import org.springframework.stereotype.Service;

import com.kdnadev.proyectofinal_santiagocabrera.repository.AdopcionRepository;
import com.kdnadev.proyectofinal_santiagocabrera.repository.MascotaRepository;
import com.kdnadev.proyectofinal_santiagocabrera.repository.UsuarioRepository;
import com.kdnadev.proyectofinal_santiagocabrera.common.utils.Utils;
import com.kdnadev.proyectofinal_santiagocabrera.exception.ResourceNotFoundException;
import com.kdnadev.proyectofinal_santiagocabrera.exception.ValidacionNegocioException;
import com.kdnadev.proyectofinal_santiagocabrera.model.Adopcion;
import com.kdnadev.proyectofinal_santiagocabrera.model.Mascota;
import com.kdnadev.proyectofinal_santiagocabrera.model.Usuario;

@Service
public class AdopcionService {
    private AdopcionRepository adopcionRepository;
    private MascotaRepository mascotaRepository;
    private UsuarioRepository usuarioRepository;

    public AdopcionService(AdopcionRepository adopcionRepository, MascotaRepository mascotaRepository, UsuarioRepository usuarioRepository) {
        this.adopcionRepository = adopcionRepository;
        this.mascotaRepository = mascotaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Adopcion create(Adopcion adopcion) {
        Usuario usuario = usuarioRepository.findById(adopcion.getIdUsuario())
            .orElseThrow(() -> new ValidacionNegocioException("Usuario no encontrado"));

        Mascota mascota = mascotaRepository.findById(adopcion.getIdMascota())
            .orElseThrow(() -> new ValidacionNegocioException("Mascota no encontrada"));

        if (!mascota.isDisponibleParaAdopcion()) {
            throw new ValidacionNegocioException("Mascota no disponible para adopcion");
        }

        // En el modelo se maneja el error de maximo numero de mascotas
        usuario.incrementaNumeroDeAdopciones();
        usuarioRepository.save(usuario);
        mascotaRepository.setDisponibleParaAdopcionById(adopcion.getIdMascota(), false);
        adopcion.setFechaAdopcion(Utils.getCurrentDate());
        return adopcionRepository.save(adopcion);
    }

    public void delete(Long id) {
        // Buscar la adopción primero para evitar múltiples consultas
        Adopcion adopcion = adopcionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró el vínculo de adopción con ID: " + id));

        Usuario usuario = usuarioRepository.findById(adopcion.getIdUsuario())
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        adopcionRepository.delete(adopcion);
        mascotaRepository.setDisponibleParaAdopcionById(adopcion.getIdMascota(), true);
        usuario.decrementaNumeroDeAdopciones();
        usuarioRepository.save(usuario);
    }
}
