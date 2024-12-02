package com.kdnadev.proyectofinal_santiagocabrera.common.response;

import java.util.Collections;
import java.util.List;

import com.kdnadev.proyectofinal_santiagocabrera.model.Mascota;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MascotaResponse")
public class MascotaResponse extends GenericResponse<List<Mascota>> {
    public MascotaResponse(Mascota data){
        super(Collections.singletonList(data));
    }

    public MascotaResponse(List<Mascota> mascotas){
        super(mascotas);
    }
}
