package com.kdnadev.proyectofinal_santiagocabrera.common.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiResponse<T> {
    private T data;
    private String mensaje;
    private String error;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
    private boolean exito;

    // Constructor para respuestas exitosas
    public ApiResponse(T data) {
        this.data = data;
        this.exito = true;
        this.timestamp = LocalDateTime.now();
    }

    // Constructor para respuestas de error
    public ApiResponse(T data, String mensaje, String error) {
        this.data = data;
        this.mensaje = mensaje;
        this.error = error;
        this.exito = false;
        this.timestamp = LocalDateTime.now();
    }

    // Getters y setters
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

}