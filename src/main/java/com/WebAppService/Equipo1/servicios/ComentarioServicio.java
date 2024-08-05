package com.WebAppService.Equipo1.servicios;

import com.WebAppService.Equipo1.entidad.Comentario;
import com.WebAppService.Equipo1.entidad.Contrato;
import com.WebAppService.Equipo1.entidad.Proveedor;
import com.WebAppService.Equipo1.enums.EstadoComentario;
import com.WebAppService.Equipo1.repositorios.ComentarioRepositorio;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ComentarioServicio {

    @Autowired
    private ContratoServicio contratoServicio;
    @Autowired
    private ComentarioRepositorio comentarioRepositorio;
    @Autowired
    private ProveedorServicio proveedorServicio;

    @Transactional
    public void comentarContrato(Integer idContrato, String comentarioTxt) {
        Contrato contrato = contratoServicio.getOne(idContrato);

        if (contrato != null) {
            Comentario comentario = new Comentario();
            comentario.setComentario(comentarioTxt);
            comentario.setEstado(EstadoComentario.VISIBLE);
            comentario.setContrato(contrato);
            Proveedor proveedor = proveedorServicio.getOne(contrato.getProveedor().getId());
            comentario.setProveedor(proveedor);
            comentarioRepositorio.save(comentario);
        }
    }

    @Transactional
    public void denunciarComentario(Integer idComentario) {
        Comentario comentario = comentarioRepositorio.getOne(idComentario);       
        if (comentario != null) {
            comentario.setEstado(EstadoComentario.DENUNCIADO);
        }
    }
    
    @Transactional
    public void censurarComentario(Integer idComentario) {
        Comentario comentario = comentarioRepositorio.getOne(idComentario);       
        if (comentario != null) {
            comentario.setEstado(EstadoComentario.CENSURADO);
        }
    }
    
    @Transactional
    public void destacarComentario(Integer idComentario) {
        Comentario comentario = comentarioRepositorio.getOne(idComentario);       
        if (comentario != null) {
            comentario.setEstado(EstadoComentario.DESTACADO);
        }
    }

    @Transactional
    public List<Comentario> listarPorProveedor(Integer proveedorId) {
        return comentarioRepositorio.buscarPorProveedor(proveedorId);
    }
    
    @Transactional
    public List<Comentario> listarPorUsuario(Integer usuarioId) {
        return comentarioRepositorio.buscarPorUsuario(usuarioId);
    }
    
    @Transactional
    public List<Comentario> listarPorEstado(EstadoComentario estado) {
        List <Comentario> destacados = comentarioRepositorio.buscarPorEstado(estado);
        return destacados.size() > 3 ? destacados.subList(0, 3) : destacados;
    }
    
    @Transactional
    public List<Comentario> listarComentarios() {
        return comentarioRepositorio.findAll(Sort.by(Sort.Direction.DESC,"id"));
    }

}
