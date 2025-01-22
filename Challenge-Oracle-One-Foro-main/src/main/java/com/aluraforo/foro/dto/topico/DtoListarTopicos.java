package com.aluraforo.foro.dto.topico;

import java.time.LocalDateTime;

import com.aluraforo.foro.modelo.Topico;

public record DtoListarTopicos (Long id,String titulo, String mensaje,LocalDateTime fechaCreacion, 
String status, String autor, String curso){
    public DtoListarTopicos(Topico topico){
        this(topico.getId(),topico.getTitulo(),topico.getMensaje(),topico.getFecha_creacion(),
        topico.getEstatus().toString(),topico.getAutor().getNombre(),topico.getCurso().getTitulo());
    }
}
