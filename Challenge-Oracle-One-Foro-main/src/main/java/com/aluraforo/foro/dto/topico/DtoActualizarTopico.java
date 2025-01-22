package com.aluraforo.foro.dto.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DtoActualizarTopico (
    @NotNull Long id, @NotBlank String titulo,@NotBlank String mensaje ) {
}
