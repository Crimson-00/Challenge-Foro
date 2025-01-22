package com.aluraforo.foro.controller;

import java.net.URI;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.aluraforo.foro.dto.usuario.DtoActualizarUsuario;
import com.aluraforo.foro.dto.usuario.DtoDetalleUsuario;
import com.aluraforo.foro.dto.usuario.DtoRegistroUsuario;
import com.aluraforo.foro.dto.usuario.DtoRespuestaRegistroUsuario;
import com.aluraforo.foro.modelo.Usuario;
import com.aluraforo.foro.service.UsuarioService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/usuario")


@SecurityRequirement(name = "bearer-key")
public class UsuarioController {
     @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<DtoRespuestaRegistroUsuario> RegistrarUsuario (@RequestBody @Valid DtoRegistroUsuario dtoRegistroUsuario,
    UriComponentsBuilder uriComponentsBuilder){
        DtoRespuestaRegistroUsuario usuario = usuarioService.RegistrarUsuario(dtoRegistroUsuario);
        URI url = uriComponentsBuilder.path("/usuario/{id}").buildAndExpand(usuario.id()).toUri();
        return ResponseEntity.created(url).body(usuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> ListarTopicos () {
        return ResponseEntity.ok(usuarioService.ListarUsuario());
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<DtoDetalleUsuario> DetalleUsuario (@PathVariable Long id) {
        return ResponseEntity.ok().body(usuarioService.DetalleUsuario(id));
    }
    
    @PutMapping()
    public ResponseEntity<DtoDetalleUsuario> ActualizarTOpico (@RequestBody @Valid DtoActualizarUsuario dtoActualizarUsuario){
        return ResponseEntity.ok(usuarioService.actualizarUsuario(dtoActualizarUsuario));
    }
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> EliminarUsuario (@PathVariable Long id) {
        usuarioService.EliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
