package com.kdnadev.proyectofinal_santiagocabrera.common.response;

import java.util.Collections;
import java.util.List;

import com.kdnadev.proyectofinal_santiagocabrera.model.Usuario;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UsuarioResponse")
public class UsuarioResponse extends GenericResponse<List<Usuario>>{
    public UsuarioResponse(Usuario usuario){
        super(Collections.singletonList(usuario));
    }

    public UsuarioResponse(List<Usuario> usuarios){
        super(usuarios);
    }
}
