package com.aluraforo.foro.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.aluraforo.foro.repository.UsuariosRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter{
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuariosRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                //Obtener token del header
                var authHeader = request.getHeader("Authorization");
                if (authHeader != null) {
                    // Sino es null hacemos el replace
                    var token = authHeader.replace("Bearer ", "");
                    // Obtenemos el subject
                    var subject = tokenService.getSubject(token); // Extraemos el username
                    System.out.println(subject);
                    if ( subject != null) {
                        //Token valido
                        //Encontramos el usuario
                        var usuario = usuarioRepository.findByEmail(subject);
                        // LE decimos a spring que el login es valido
                        var authentication = new UsernamePasswordAuthenticationToken(usuario,null,
                        usuario.getAuthorities());// Forzamos un inicio de sesion
                        // Setiamos manualmente el inicio de sesion
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
               filterChain.doFilter(request, response);
    }
}
