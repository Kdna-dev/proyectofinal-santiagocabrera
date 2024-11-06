package com.kdnadev.proyectofinal_santiagocabrera.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kdnadev.proyectofinal_santiagocabrera.repository.AdopcionRepository;
import com.kdnadev.proyectofinal_santiagocabrera.repository.MascotaRepository;
import com.kdnadev.proyectofinal_santiagocabrera.repository.UsuarioRepository;
import com.kdnadev.proyectofinal_santiagocabrera.common.utils.Utils;
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
        Optional<Usuario> usuario = usuarioRepository.findById(adopcion.getIdUsuario());
        if (!usuario.isPresent()) {
            throw new ValidacionNegocioException("Usuario no encontrado");
        }

        Optional<Mascota> mascota = mascotaRepository.findById(adopcion.getIdMascota());
        if (!mascota.isPresent()) {
            throw new ValidacionNegocioException("Mascota no encontrada");
        }

        if (!mascota.get().isDisponibleParaAdopcion()) {
            throw new ValidacionNegocioException("Mascota no disponible para adopcion");
        }

        mascotaRepository.setDisponibleParaAdopcionById(adopcion.getIdMascota(), false);
        adopcion.setFechaAdopcion(Utils.getCurrentDate());
        return adopcionRepository.save(adopcion);
    }
}
