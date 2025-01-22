package com.aluraforo.foro.dto.topico;

import com.aluraforo.foro.modelo.StatusTopic;

public record DtoRespuestaRegistroTopico(Long id,String titulo, String mensaje, StatusTopic statusTopic) {
    
}
