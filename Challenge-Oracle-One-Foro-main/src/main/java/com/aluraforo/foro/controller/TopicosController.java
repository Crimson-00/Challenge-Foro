package com.aluraforo.foro.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.aluraforo.foro.dto.topico.DtoActualizarTopico;
import com.aluraforo.foro.dto.topico.DtoDetalleTopico;
import com.aluraforo.foro.dto.topico.DtoListarTopicos;
import com.aluraforo.foro.dto.topico.DtoRegistroTopico;
import com.aluraforo.foro.dto.topico.DtoRespuestaRegistroTopico;
import com.aluraforo.foro.service.TopicosService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicosController {

    @Autowired
    private TopicosService topicosService;

    @PostMapping
    public ResponseEntity<DtoRespuestaRegistroTopico> RegistrarTopico(
            @RequestBody @Valid DtoRegistroTopico dtoRegistroTopico,
            UriComponentsBuilder uriComponentsBuilder) {
        DtoRespuestaRegistroTopico topico = topicosService.RegistrarTopico(dtoRegistroTopico);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.id()).toUri();
        return ResponseEntity.created(url).body(topico);
    }

    @GetMapping
    public ResponseEntity<Page<DtoListarTopicos>> ListarTopicos(@PathParam(value = "page") Integer page, 
    @PathParam(value = "size") Integer size, @PathParam(value = "sort") String sort) {
     
        return ResponseEntity.ok().body(topicosService.ListarTopicos(page,size,sort).map(DtoListarTopicos::new));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DtoDetalleTopico> DetalleTopico(@PathVariable Long id) {
        return ResponseEntity.ok().body(topicosService.DetalleTopico(id));
    }

    @PutMapping()
    public ResponseEntity<DtoDetalleTopico> ActualizarTOpico(
            @RequestBody @Valid DtoActualizarTopico dtoActualizarTopico) {
        return ResponseEntity.ok(topicosService.ActualizarTopico(dtoActualizarTopico));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> EliminarTopico(@PathVariable Long id) {
        topicosService.EliminarTopico(id);
        return ResponseEntity.noContent().build();
    }
}
