package com.kdnadev.proyectofinal_santiagocabrera.common.response;

import java.util.Collections;
import java.util.List;

import com.kdnadev.proyectofinal_santiagocabrera.model.TipoMascota;

public class TipoMascotaResponse extends GenericResponse<List<TipoMascota>>{
    public TipoMascotaResponse(TipoMascota tipo){
        super(Collections.singletonList(tipo));
    }

    public TipoMascotaResponse(List<TipoMascota> tipos){
        super(tipos);
    }
}
