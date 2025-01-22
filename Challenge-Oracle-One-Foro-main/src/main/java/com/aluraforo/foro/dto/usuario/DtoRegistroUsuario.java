package com.aluraforo.foro.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public record DtoRegistroUsuario(
@NotBlank    
String nombre, 
@NotBlank
String email,
@NotBlank
String contraseña) {
    
}
