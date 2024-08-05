package com.WebAppService.Equipo1.servicios;

import com.WebAppService.Equipo1.entidad.*;
import com.WebAppService.Equipo1.enums.Estado;
import com.WebAppService.Equipo1.repositorios.*;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

@Service
public class ContratoServicio {

    private final ContratoRepositorio contratoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final ProveedorRepositorio proveedorRepositorio;

    public ContratoServicio(ContratoRepositorio contratoRepositorio, UsuarioRepositorio usuarioRepositorio, ProveedorRepositorio proveedorRepositorio) {
        this.contratoRepositorio = contratoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.proveedorRepositorio = proveedorRepositorio;
    }
    
    @Transactional
    public void solicitarContrato(Integer usuarioId, Integer proveedorId, String descripcion, String direccionDelContrato,String barrio, Date fechaInicio) {
        Usuario usuario = usuarioRepositorio.findById(Math.toIntExact(usuarioId))
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID: " + usuarioId));

        Proveedor proveedor = proveedorRepositorio.findById(proveedorId)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado con el ID: " + proveedorId));

        Contrato contrato = new Contrato();
        contrato.setUsuario(usuario);
        contrato.setProveedor(proveedor);
        contrato.setDescripcionDelContrato(descripcion);
        contrato.setDireccionDelContrato(direccionDelContrato);
        contrato.setBarrio(barrio);
        contrato.setFechaDeFirma(fechaInicio);
        contrato.setEstado(Estado.PENDIENTE);

        contratoRepositorio.save(contrato);
    }
    
//    @Transactional
//    public void modificarContrato(Integer id, Integer usuarioId, Integer proveedorId, String descripcion, String direccionDelContrato,String barrio, Date fechaInicio) {
//        
//        Optional<Contrato> respuesta = contratoRepositorio.findById(id);
//
//        Contrato contrato = respuesta.get();
//        
//        Usuario usuario = usuarioRepositorio.findById(Math.toIntExact(usuarioId))
//                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID: " + usuarioId));
//
//        Proveedor proveedor = proveedorRepositorio.findById(proveedorId)
//                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado con el ID: " + proveedorId));
//
//        
//        contrato.setUsuario(usuario);
//        contrato.setProveedor(proveedor);
//        contrato.setDescripcionDelContrato(descripcion);
//        contrato.setDireccionDelContrato(direccionDelContrato);
//        contrato.setBarrio(barrio);
//        contrato.setFechaDeFirma(fechaInicio);
//        contrato.setEstado(Estado.PENDIENTE);
//
//        contratoRepositorio.save(contrato);
//    }
    
    @Transactional
    public void cancelarContrato(Integer contratoId) {
        Optional<Contrato> respuesta = contratoRepositorio.findById(contratoId);       
        if (respuesta.isPresent()) {

            Contrato contrato = respuesta.get();
            contrato.setEstado(Estado.CANCELADO);
            contratoRepositorio.save(contrato);
        }
    }
    
    @Transactional
    public void aceptarContrato(Integer contratoId) {
        Optional<Contrato> respuesta = contratoRepositorio.findById(contratoId);       
        if (respuesta.isPresent()) {

            Contrato contrato = respuesta.get();
            contrato.setEstado(Estado.EN_PROCESO);
            contratoRepositorio.save(contrato);
        }
    }
    
    @Transactional
    public void finalizarContrato(Integer contratoId,Date fechaCulminacion,Integer calificacion) {
        Optional<Contrato> respuesta = contratoRepositorio.findById(contratoId);       
        if (respuesta.isPresent()) {

            Contrato contrato = respuesta.get();
            contrato.setEstado(Estado.FINALIZADO);
            contrato.setFechaCulminacion(fechaCulminacion);
            contrato.setCalificacion(calificacion);
            contrato.setEstado(Estado.CALIFICADO);
            contratoRepositorio.save(contrato);
        }
    }
    
    @Transactional
    public void finalizarContratoProveedor(Integer contratoId,Date fechaCulminacion) {
        Optional<Contrato> respuesta = contratoRepositorio.findById(contratoId);       
        if (respuesta.isPresent()) {

            Contrato contrato = respuesta.get();
            contrato.setEstado(Estado.FINALIZADO);
            contrato.setFechaCulminacion(fechaCulminacion);           
            contratoRepositorio.save(contrato);
        }
    }
    
//    @Transactional
//    public void calificarContrato(Integer Id,Integer calificacion){
//        Optional<Contrato> respuesta = contratoRepositorio.findById(Id);
//        if(respuesta.isPresent()){
//            Contrato contrato=respuesta.get();
//            contrato.setCalificacion(calificacion);
//            contratoRepositorio.save(contrato);
//        }
//    }

    @Transactional
    public List<Contrato> listarContratosUsuario(Integer usuarioId) {
        return contratoRepositorio.buscarPorUsuario(usuarioId);
    }
    
    @Transactional
    public List<Contrato> listarContratosProveedor(Integer proveedorId) {
        return contratoRepositorio.buscarPorProveedor(proveedorId);

    }
    
    @Transactional
    public Contrato getOne(Integer id){
        return contratoRepositorio.getOne(id);
    }

//    public Contrato solicitarContrato(Integer usuarioId, Integer proveedorId, String descripcion, String direccionDelContrato) {
//        Usuario usuario = usuarioRepositorio.findById(Math.toIntExact(usuarioId))
//                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID: " + usuarioId));
//
//        Proveedor proveedor = proveedorRepositorio.findById(proveedorId)
//                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado con el ID: " + proveedorId));
//
//        Contrato contrato = new Contrato();
//        contrato.setUsuario(usuario);
//        contrato.setProveedor(proveedor);
//        contrato.setDescripcionDelContrato(descripcion);
//        contrato.setDireccionDelContrato(direccionDelContrato);
//        contrato.setFechaDeFirma(new Date());
//
//        return contratoRepositorio.save(contrato);
//    }
}
