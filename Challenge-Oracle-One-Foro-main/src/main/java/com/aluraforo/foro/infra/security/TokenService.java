package com.aluraforo.foro.infra.security;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.aluraforo.foro.infra.errors.Validacion;
import com.aluraforo.foro.modelo.Usuario;


@Service
public class TokenService {
   
    @Value("${api.security.secret}")
    private String apiSecret;
    public String generarToken (Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
            .withIssuer("foro_alura")
            .withSubject(usuario.getEmail())
            .withClaim("id", usuario.getId())
            .withExpiresAt(generarFechaExpiracion())
            .sign(algorithm);
        } catch (JWTVerificationException exception){
            throw new RuntimeException(exception);
        }
    }

    public String getSubject (String token ){
        if (token == null) {
            throw new Validacion("Token nulo");
        }
        String subject = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            DecodedJWT verifier = JWT.require(algorithm)
                .withIssuer("foro_alura")
                .build()
                .verify(token);
            subject = verifier.getSubject();    
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Error en token ");
        }
        return subject;
    }

    public Instant generarFechaExpiracion () {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00")); 
    }
}
