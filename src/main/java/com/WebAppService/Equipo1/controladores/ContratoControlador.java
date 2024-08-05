package com.WebAppService.Equipo1.controladores;

import com.WebAppService.Equipo1.entidad.*;
import com.WebAppService.Equipo1.repositorios.*;
import com.WebAppService.Equipo1.servicios.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_PROVEEDOR')")
@Controller
@RequestMapping("/contrato")
public class ContratoControlador {

    @Autowired
    private ContratoServicio contratoServicio;
    @Autowired
    private ContratoRepositorio contratoRepositorio;
    @Autowired
    private ProveedorServicio proveedorServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ComentarioServicio comentarioServicio;

    @GetMapping("/solicitar")
    public String solicitarContrato(@RequestParam("proveedorId") Integer proveedorId, ModelMap model) {
        // Obtener datos del proveedor
        Proveedor proveedor = proveedorServicio.getOne(proveedorId);
        model.addAttribute("proveedor", proveedor);
        //model.addAttribute("proveedorId", proveedorId);
        return "contrato_proveedor";
    }

    @PostMapping("/crearContrato")
    public String crearContrato(@RequestParam("proveedorId") Integer proveedorId, @RequestParam("descripcion_servicio") String descripcionServicio, @RequestParam("barrio") String barrio, @RequestParam("direccion") String direccion, @RequestParam("fecha_inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio, ModelMap model, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        try {

            contratoServicio.solicitarContrato(usuario.getId(), proveedorId, descripcionServicio, direccion, barrio, fechaInicio);
            model.put("exito", "contrato creado con exito!");
            return "redirect:/inicio";
        } catch (Exception ex) {
            model.put("error", ex.getMessage());
            return "contrato_proveedor";
        }
        
    }

    @GetMapping("/cancelar/{id}")
    public String cancelarContrato(@PathVariable Integer id, ModelMap model) {
        contratoServicio.cancelarContrato(id);
        model.put("mensaje", "Contrato cancelado con éxito");
        return "redirect:/inicio";
    }

    @GetMapping("/aceptar/{id}")
    public String aceptarContrato(@PathVariable Integer id, ModelMap model) {
        contratoServicio.aceptarContrato(id);
        model.put("mensaje", "Contrato aceptado con éxito");
        return "redirect:/inicio";
    }

    @PostMapping("/finalizar")
    public String finalizarContrato(@RequestParam("id") Integer id, @RequestParam("fecha_culminacion") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaCulminacion) {
        System.out.println("Fecha de culminación recibida: " + fechaCulminacion);
        contratoServicio.finalizarContratoProveedor(id, fechaCulminacion);
        //model.put("mensaje", "Contrato finalizado con éxito");
        return "redirect:/inicio";
    }
    
//    @GetMapping("/calificar/{id}")
//public String calificar(@PathVariable("id") Integer contratoId, ModelMap modelo) {
//    Contrato contrato = contratoServicio.getOne(contratoId);
//        System.out.println("********* " + contrato.getId());
//    modelo.put("contrato", contrato);
//    return "inicio";
//}

    @PostMapping("/calificacion")
    public String calificarContrato(@RequestParam("contratoId") Integer contratoId, @RequestParam("descripcion_servicio") String comentario, @RequestParam("calificacion") Integer calificacion,@RequestParam("fecha_culminacion") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaCulminacion) {
        
            comentarioServicio.comentarContrato(contratoId, comentario);
            contratoServicio.finalizarContrato(contratoId,fechaCulminacion, calificacion);
            Contrato contrato= contratoServicio.getOne(contratoId);
            Proveedor proveedor= contrato.getProveedor();
            proveedorServicio.calificacion(proveedor.getId());
//            Double calificacionPromedio = proveedorServicio.calificacion(proveedor.getId());
//            proveedor.setCalificacion(calificacionPromedio);
            
            return "redirect:/inicio";
        
    }

}
