package com.aluraforo.foro.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacionUsuario (@NotBlank String email, @NotBlank String contraseña){

}
