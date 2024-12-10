package com.kdnadev.proyectofinal_santiagocabrera.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdnadev.proyectofinal_santiagocabrera.common.response.UsuarioResponse;
import com.kdnadev.proyectofinal_santiagocabrera.dto.usuario.UsuarioCreateDTO;
import com.kdnadev.proyectofinal_santiagocabrera.dto.usuario.UsuarioMapper;
import com.kdnadev.proyectofinal_santiagocabrera.dto.usuario.UsuarioUpdateDTO;
import com.kdnadev.proyectofinal_santiagocabrera.exception.ResourceNotFoundException;
import com.kdnadev.proyectofinal_santiagocabrera.model.Rol;
import com.kdnadev.proyectofinal_santiagocabrera.model.Usuario;
import com.kdnadev.proyectofinal_santiagocabrera.service.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private UsuarioService usuarioService;
    private UsuarioMapper usuarioMapper;

    public UsuarioController (UsuarioService usuarioService, UsuarioMapper usuarioMapper){
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> getAll() {
        List<Usuario> usuarios = usuarioService.getAll();
        return ResponseEntity.ok(new UsuarioResponse(usuarioMapper.toDTO(usuarios)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<UsuarioResponse> getById(@PathVariable Long id){
        Usuario usuarioEncontrado = usuarioService.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontro el usuario con id: " + id));

        return ResponseEntity.ok(new UsuarioResponse(usuarioMapper.toDTO(usuarioEncontrado)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> update(@PathVariable Long id, @RequestBody UsuarioUpdateDTO usuario) {
        return usuarioService.findById(id)
            .map(usuarioExistente -> {
                Usuario usuarioActualizado = usuarioService.update(id, usuario);
                return ResponseEntity.ok(new UsuarioResponse(usuarioMapper.toDTO(usuarioActualizado)));
            })
            .orElseThrow(() -> new ResourceNotFoundException("No se encontro el usuario con el id: " + id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/registro/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> registrarAdmin(@RequestBody UsuarioCreateDTO usuario) {
        Usuario usuarioCreado = usuarioService.create(usuario, Rol.ADMIN.getCodigo());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new UsuarioResponse(usuarioMapper.toDTO(usuarioCreado)));
    }

    @PostMapping("/registro/doctor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> registrarDoctor(@RequestBody UsuarioCreateDTO usuario) {
        Usuario usuarioCreado = usuarioService.create(usuario, Rol.DOCTOR.getCodigo());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new UsuarioResponse(usuarioMapper.toDTO(usuarioCreado)));
    }

    @PostMapping("/registro/cliente")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<UsuarioResponse> registrarCliente(@RequestBody UsuarioCreateDTO usuario) {
        Usuario usuarioCreado = usuarioService.create(usuario, Rol.CLIENTE.getCodigo());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new UsuarioResponse(usuarioMapper.toDTO(usuarioCreado)));
    }
}
