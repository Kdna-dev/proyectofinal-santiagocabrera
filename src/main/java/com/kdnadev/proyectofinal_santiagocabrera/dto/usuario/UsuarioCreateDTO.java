package com.kdnadev.proyectofinal_santiagocabrera.dto.usuario;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class UsuarioCreateDTO {
    private String nombre;
    private String email;
    private String telefono;
    private String username;
    private String password;

    public UsuarioCreateDTO() {
    }

    public UsuarioCreateDTO(String nombre, String email, String telefono, String username, String password) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.username = username;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        this.nombre = nombre;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser nulo o vacío");
        }
        this.email = email;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        if (telefono == null || telefono.isEmpty()) {
            throw new IllegalArgumentException("El teléfono no puede ser nulo o vacío");
        }
        this.telefono = telefono;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede ser nulo o vacío");
        }
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía");
        }
        this.password = password;
    }
}
