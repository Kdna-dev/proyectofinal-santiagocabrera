package com.kdnadev.proyectofinal_santiagocabrera.common.response;

import java.util.Collections;
import java.util.List;

import com.kdnadev.proyectofinal_santiagocabrera.model.Adopcion;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AdopcionResponse")
public class AdopcionResponse extends GenericResponse<List<Adopcion>>{
    public AdopcionResponse(Adopcion usuario){
        super(Collections.singletonList(usuario));
    }

    public AdopcionResponse(List<Adopcion> usuarios){
        super(usuarios);
    }
}

