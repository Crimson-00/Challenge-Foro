package com.aluraforo.foro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aluraforo.foro.modelo.Topico;

public interface TopicosRepository extends JpaRepository<Topico, Long>{
    
    <T> Optional<T> findByTitulo(String titulo);

    <T> Optional<T> findByMensaje(String mensaje);

  }
