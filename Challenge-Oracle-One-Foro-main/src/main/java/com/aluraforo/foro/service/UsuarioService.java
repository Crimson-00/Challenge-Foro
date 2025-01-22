package com.aluraforo.foro.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aluraforo.foro.dto.usuario.DtoActualizarUsuario;
import com.aluraforo.foro.dto.usuario.DtoDetalleUsuario;
import com.aluraforo.foro.dto.usuario.DtoRegistroUsuario;
import com.aluraforo.foro.dto.usuario.DtoRespuestaRegistroUsuario;
import com.aluraforo.foro.infra.errors.Validacion;
import com.aluraforo.foro.modelo.Usuario;
import com.aluraforo.foro.repository.UsuariosRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuariosRepository usuariosRepository;

    public DtoRespuestaRegistroUsuario RegistrarUsuario(DtoRegistroUsuario dtoRegistroUsuario) {
        var existeUsuario = usuariosRepository.existsByEmail(dtoRegistroUsuario.email());
        System.out.println(existeUsuario);
        if (existeUsuario) {
            throw new Validacion("El email utilizado ya existe");
        }
        Usuario usuario = new Usuario(null, dtoRegistroUsuario.nombre(),dtoRegistroUsuario.email(),dtoRegistroUsuario.contraseña());
        usuariosRepository.save(usuario);
        return new DtoRespuestaRegistroUsuario(usuario.getId(),usuario.getNombre(),usuario.getEmail()); 
    }

    public List<Usuario> ListarUsuario() {
        var listarUsuarios = usuariosRepository.findAll();
        return listarUsuarios;
    }

    public DtoDetalleUsuario DetalleUsuario(Long id) {
        if (!usuariosRepository.existsById(id)) {
            throw new Validacion("El usuario con el id indicado no existe.");
        }
        Usuario usuario = usuariosRepository.findById(id).get();
        return new DtoDetalleUsuario(usuario.getId(),usuario.getNombre(),usuario.getEmail());
    }

    public DtoDetalleUsuario actualizarUsuario(DtoActualizarUsuario dtoActualizarUsuario) {
         if (!usuariosRepository.existsById(dtoActualizarUsuario.id())) {
            throw new Validacion("El usuario con el id indicado no existe.");
        }
        var usuario = usuariosRepository.getReferenceById(dtoActualizarUsuario.id());
        if (dtoActualizarUsuario.nombre() != null) {
                usuario.setNombre(dtoActualizarUsuario.nombre());;
            }
            if (dtoActualizarUsuario.email() != null) {
                usuario.setEmail(dtoActualizarUsuario.email());;
            }
            usuariosRepository.save(usuario);
            return new DtoDetalleUsuario(usuario.getId(),usuario.getNombre(),usuario.getEmail());
    }

    public void EliminarUsuario(Long id) {
        if (!usuariosRepository.existsById(id)) {
            throw new Validacion("El topico con el id indicado no existe.");
        }
        usuariosRepository.deleteById(id);
    }
}
