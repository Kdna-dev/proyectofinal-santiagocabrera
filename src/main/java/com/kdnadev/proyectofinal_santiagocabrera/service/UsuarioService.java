package com.kdnadev.proyectofinal_santiagocabrera.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kdnadev.proyectofinal_santiagocabrera.model.Usuario;
import com.kdnadev.proyectofinal_santiagocabrera.repository.UsuarioRepository;

@Service
@Transactional(readOnly = true)
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getAll() {
        return usuarioRepository.findAllWithRoles();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findByIdWithRoles(id);
    }

    @Transactional(readOnly = false)
    public Optional<Usuario> deleteById(Long id){
        return usuarioRepository.findById(id)
            .map(usuarioEliminado -> {
                usuarioRepository.delete(usuarioEliminado);
                return usuarioEliminado;
            });
    }

    @Transactional(readOnly = false)
    public Usuario create(Usuario usuario){
        Date fechaActual = new Date(System.currentTimeMillis());
        usuario.setFechaCreacion(fechaActual);

        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = false)
    public Usuario update(Long id, Usuario actualizacionUsuario){
        Usuario usuario = usuarioRepository.getReferenceById(id);

        usuario.setEmail(actualizacionUsuario.getEmail());
        usuario.setNombre(actualizacionUsuario.getNombre());
        usuario.setTelefono(actualizacionUsuario.getTelefono());

        Date fechaActual = new Date(System.currentTimeMillis());
        usuario.setFechaActualizacion(fechaActual);

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findByUsername(String string) {
        return usuarioRepository.findByUsername(string);
    }
}
