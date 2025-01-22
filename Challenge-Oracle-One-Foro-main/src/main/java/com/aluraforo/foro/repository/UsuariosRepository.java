package com.aluraforo.foro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.aluraforo.foro.modelo.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Long>{

   UserDetails findByEmail(String email);

   Boolean existsByEmail(String email);
}
