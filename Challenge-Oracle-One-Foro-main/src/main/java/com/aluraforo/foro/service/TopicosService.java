package com.aluraforo.foro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.aluraforo.foro.dto.topico.DtoActualizarTopico;
import com.aluraforo.foro.dto.topico.DtoDetalleTopico;
import com.aluraforo.foro.dto.topico.DtoRegistroTopico;
import com.aluraforo.foro.dto.topico.DtoRespuestaRegistroTopico;
import com.aluraforo.foro.infra.errors.Validacion;
import com.aluraforo.foro.modelo.Topico;
import com.aluraforo.foro.repository.CursosRepository;
import com.aluraforo.foro.repository.TopicosRepository;
import com.aluraforo.foro.repository.UsuariosRepository;

@Service
public class TopicosService {
    @Autowired
    private CursosRepository cursosRepository;
    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private TopicosRepository topicosRepository;

    public DtoRespuestaRegistroTopico RegistrarTopico(DtoRegistroTopico dtoRegistroTopico) {
        var existeTopico = existeTopico(dtoRegistroTopico);
        if (!existeTopico) {
            var curso = cursosRepository.getReferenceById(dtoRegistroTopico.id_curso());
            var usuario = usuariosRepository.getReferenceById(dtoRegistroTopico.id_usuario());
            var topico = new Topico(dtoRegistroTopico.titulo(), dtoRegistroTopico.mensaje(), usuario, curso);
            topicosRepository.save(topico);

            return new DtoRespuestaRegistroTopico(
                    topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getEstatus());
        }
        return null;
    }

    public Page<Topico> ListarTopicos(Integer numPage,Integer numSize, String sort) {
        if(numPage == null) {
            numPage = 0;
        }
        if(numSize == null) {
            numSize = 5;
        }
        if(sort == null){
            sort = "id";
        }
        PageRequest page = PageRequest.of(numPage, numSize, Sort.by(sort).ascending());
        Page<Topico> topico = topicosRepository.findAll(page);
        return topico;
    }

    public DtoDetalleTopico DetalleTopico(Long id) {
        if (!topicosRepository.existsById(id)) {
            throw new Validacion("El topico con el id indicado no existe.");
        }
        Topico topico = topicosRepository.findById(id).get();
        DtoDetalleTopico dto = new DtoDetalleTopico(
                topico.getTitulo(), topico.getMensaje(),
                topico.getFecha_creacion(), topico.getEstatus().toString(), topico.getAutor().getNombre(),
                topico.getCurso().getTitulo());
        return dto;
    }

    public DtoDetalleTopico ActualizarTopico(DtoActualizarTopico dtoActualizarTopico) {
        if (!topicosRepository.existsById(dtoActualizarTopico.id())) {
            throw new Validacion("El topico con el id indicado no existe.");
        }
        var existeTopico = existeTopico(dtoActualizarTopico);
        var topico = topicosRepository.getReferenceById(dtoActualizarTopico.id());

        if (!existeTopico) {
            if (dtoActualizarTopico.titulo() != null) {
                topico.setTitulo(dtoActualizarTopico.titulo());
            }
            if (dtoActualizarTopico.mensaje() != null) {
                topico.setMensaje(dtoActualizarTopico.mensaje());
            }
            topicosRepository.save(topico);
            return new DtoDetalleTopico(
                    topico.getTitulo(), topico.getMensaje(),
                    topico.getFecha_creacion(), topico.getEstatus().toString(), topico.getAutor().getNombre(),
                    topico.getCurso().getTitulo());
        }
        return null;
    }

    public void EliminarTopico(Long id) {
        if (!topicosRepository.existsById(id)) {
            throw new Validacion("El topico con el id indicado no existe.");
        }
        topicosRepository.deleteById(id);
    }

    private Boolean existeTopico(DtoActualizarTopico dtoActualizarTopico) {
        var existeTopico = topicosRepository.findByTitulo(dtoActualizarTopico.titulo()).isPresent();
        var existeMensaje = topicosRepository.findByMensaje(dtoActualizarTopico.mensaje()).isPresent();
        if (existeTopico && existeMensaje || !existeTopico && existeMensaje) {
            throw new Validacion("El titulo/mensaje ya existen");
        }
        return false;
    }

    private Boolean existeTopico(DtoRegistroTopico dtoRegistroTopico) {
        var existeTopico = topicosRepository.findByTitulo(dtoRegistroTopico.titulo()).isPresent();
        var existeMensaje = topicosRepository.findByMensaje(dtoRegistroTopico.mensaje()).isPresent();
        if (existeTopico && existeMensaje || !existeTopico && existeMensaje) {
            throw new Validacion("El titulo/mensaje ya existen");
        }
        return false;
    }

}
