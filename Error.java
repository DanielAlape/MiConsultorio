package com.proyecto.miconsultorio.Models;

import lombok.Data;

@Data // Sirve para detectar objetos y sus ubicaciones, muy útil para encontrar errores
public class Error {
    private String field;
    private String message;
}
