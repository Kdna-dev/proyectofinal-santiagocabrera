package com.kdnadev.proyectofinal_santiagocabrera.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kdnadev.proyectofinal_santiagocabrera.common.response.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ApiResponse<String> response = new ApiResponse<>(
            null,  // data es null porque es un error
            ex.getMessage(),  // mensaje de error que obtenemos de la excepción
            "Validación fallida"  // tipo de error
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAccessDeniedException(AccessDeniedException ex) {
        ApiResponse<String> response = new ApiResponse<>(
            null,
            "No tiene permisos para realizar esta acción",
            "Access Denied"
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}