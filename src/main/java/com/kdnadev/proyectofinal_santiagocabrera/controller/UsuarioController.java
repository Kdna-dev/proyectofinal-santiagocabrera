package com.kdnadev.proyectofinal_santiagocabrera.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kdnadev.proyectofinal_santiagocabrera.model.Rol;
import com.kdnadev.proyectofinal_santiagocabrera.model.Usuario;
import com.kdnadev.proyectofinal_santiagocabrera.service.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.access.prepost.PreAuthorize;



@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private UsuarioService usuarioService;
    private PasswordEncoder passwordEncoder;

    public UsuarioController (UsuarioService usuarioService, PasswordEncoder passwordEncoder){
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> getAll() {
        List<Usuario> usuarios = usuarioService.getAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ADMIN')")
    public ResponseEntity<Usuario> getById(@PathVariable Long id){
        return usuarioService.findById(id)
            .map(usuarioExistente -> {
                return ResponseEntity.ok(usuarioExistente);
            }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        Usuario usuarioCreado = usuarioService.create(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.findById(id)
            .map(usuarioExistente -> {
                Usuario usuarioActualizado = usuarioService.update(id, usuario);
                return ResponseEntity.ok(usuarioActualizado);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        return usuarioService.deleteById(id)
            .map(usuarioEliminado ->  ResponseEntity.noContent().build())
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/registro/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> registrarAdmin(@RequestBody Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRoles(Set.of(Rol.ADMIN));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.create(usuario));
    }

    @PostMapping("/registro/doctor")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> registrarDoctor(@RequestBody Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRoles(Set.of(Rol.DOCTOR));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.create(usuario));
    }

    @PostMapping("/registro/cliente")
    public ResponseEntity<Usuario> registrarCliente(@RequestBody Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRoles(Set.of(Rol.CLIENTE));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.create(usuario));
    }
}
