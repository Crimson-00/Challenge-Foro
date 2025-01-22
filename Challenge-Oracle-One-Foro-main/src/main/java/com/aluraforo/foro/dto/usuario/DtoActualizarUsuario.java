package com.aluraforo.foro.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DtoActualizarUsuario (
    @NotNull Long id, @NotBlank String nombre,@NotBlank String email ) {

 }
