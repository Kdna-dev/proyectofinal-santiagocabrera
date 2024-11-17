package com.kdnadev.proyectofinal_santiagocabrera.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kdnadev.proyectofinal_santiagocabrera.model.Rol;
import com.kdnadev.proyectofinal_santiagocabrera.model.Usuario;
import com.kdnadev.proyectofinal_santiagocabrera.service.UsuarioService;

import java.util.Set;

@Configuration
public class InitialDataConfig {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @jakarta.annotation.PostConstruct
    public void init() {
        // Verifica si ya existe un admin
        if (usuarioService.findByUsername("admin").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@sistema.com");
            admin.setNombre("Administrador");
            admin.setDocumento("0000");
            admin.setTelefono("0000");
            admin.setRoles(Set.of(Rol.ADMIN));
            usuarioService.create(admin);
        }

        //Verifica si ya existe un doctor para testing
        if (usuarioService.findByUsername("doctorTest").isEmpty()) {
            Usuario doctor = new Usuario();
            doctor.setUsername("doctorTest");
            doctor.setPassword(passwordEncoder.encode("doctor123"));
            doctor.setEmail("doctor@sistema.com");
            doctor.setNombre("Doctor");
            doctor.setDocumento("1234");
            doctor.setTelefono("0000");
            doctor.setRoles(Set.of(Rol.DOCTOR));
            usuarioService.create(doctor);
        }

        //Verifica si ya existe un cliente para testing
        if (usuarioService.findByUsername("clienteTest").isEmpty()) {
            Usuario cliente = new Usuario();
            cliente.setUsername("clienteTest");
            cliente.setPassword(passwordEncoder.encode("cliente123"));
            cliente.setEmail("cliente@sistema.com");
            cliente.setNombre("Cliente");
            cliente.setDocumento("4321");
            cliente.setTelefono("0000");
            cliente.setRoles(Set.of(Rol.CLIENTE));
            usuarioService.create(cliente);
        }

    }
}