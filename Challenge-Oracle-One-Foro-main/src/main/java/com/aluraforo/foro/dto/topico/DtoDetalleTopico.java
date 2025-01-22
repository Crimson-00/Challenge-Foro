package com.aluraforo.foro.dto.topico;

import java.time.LocalDateTime;

public record DtoDetalleTopico(String titulo, String mensaje, LocalDateTime fechaCreacion, 
String status, String autor, String curso) {
}
