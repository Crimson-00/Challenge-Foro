package com.aluraforo.foro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import com.aluraforo.foro.dto.usuario.DatosAutenticacionUsuario;
import com.aluraforo.foro.infra.security.DatosJwt;
import com.aluraforo.foro.infra.security.TokenService;
import com.aluraforo.foro.modelo.Usuario;


@RestController
@RequestMapping(path = "/login")
public class AutenticacionController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private TokenService tokenService;
    
    @PostMapping
    public ResponseEntity<Object> autenticarUsuario (@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.email()
        ,datosAutenticacionUsuario.contraseña());
        var usuarioAuthenticado = authenticationManager.authenticate(authToken);
        
        var tokenJwt = tokenService.generarToken((Usuario)usuarioAuthenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJwt(tokenJwt));
    }
}
