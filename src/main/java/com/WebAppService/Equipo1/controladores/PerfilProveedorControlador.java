/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.WebAppService.Equipo1.controladores;

import com.WebAppService.Equipo1.entidad.Comentario;
import com.WebAppService.Equipo1.entidad.Imagen;
import com.WebAppService.Equipo1.entidad.PerfilProveedor;
import com.WebAppService.Equipo1.entidad.Proveedor;
import com.WebAppService.Equipo1.excepciones.MiException;
import com.WebAppService.Equipo1.servicios.ComentarioServicio;
import com.WebAppService.Equipo1.servicios.PerfilProveedorServicio;
import com.WebAppService.Equipo1.servicios.ProveedorServicio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@PreAuthorize("hasRole('ROLE_PROVEEDOR')")
@Controller
@RequestMapping("/perfilProveedor")
public class PerfilProveedorControlador {
    
    @Autowired
    PerfilProveedorServicio perfilProveedorServicio;
    @Autowired 
    ComentarioServicio comentarioServicio;
    @Autowired
    ProveedorServicio proveedorServicio;
    
    
     @GetMapping("/verPerfilPublico/{proveedorId}")
    public String verPerfilPublico(@PathVariable("proveedorId") Integer proveedorId, ModelMap modelo) {
        Proveedor proveedor = proveedorServicio.getOne(proveedorId);

        if (proveedor == null) {
            return "redirect:/inicio";
        } else {
            PerfilProveedor perfilProveedor = proveedor.getPerfilProveedor();
            List<Comentario> comentarios = comentarioServicio.listarPorProveedor(proveedorId);
            List<Imagen> imagenes = perfilProveedor != null ? perfilProveedor.getImagenes() : new ArrayList<>();

            modelo.put("proveedor", proveedor);
            modelo.put("perfilProveedor", perfilProveedor);
            modelo.put("comentarios", comentarios);
            modelo.put("imagenes", imagenes);
            return "PROV_crearPerfilPublico.html";
        }
    }
    
    @PreAuthorize("hasRole('PROVEEDOR')")
    @GetMapping("/crearPerfilPublico")
    public String crearPerfilPublico( ModelMap modelo, HttpSession session) {
        Proveedor proveedor = (Proveedor) session.getAttribute("proveedorsession");

        if (proveedor == null ) {
            return "redirect:/inicio";
        } else {
            modelo.put("proveedor", proveedor);
            modelo.put("perfilProveedor",proveedor.getPerfilProveedor());
            return "PROV_crearPerfilPublico.html";
        }

    }
    
    @PreAuthorize("hasRole('PROVEEDOR')")
    @PostMapping("/creacionPerfilPublico")
    public String creacionPerfilPublico( ModelMap modelo,@RequestParam("descripcion") String descripcion,@RequestParam("imagenes") List <MultipartFile> imagenes, HttpSession session) {
        Proveedor proveedor = (Proveedor) session.getAttribute("proveedorsession");
        
        try{
            perfilProveedorServicio.crearPerfilPublico(proveedor.getId(), descripcion,imagenes);
            modelo.put("exito", "Perfil creado con exito!");
            return "redirect:/inicio";
        }catch(Exception ex){
            modelo.put("error", ex.getMessage());
            return "redirect:/perfilProveedor/verPerfilPublico/" + proveedor.getId();
        }
   }
    
   @PreAuthorize("hasRole('PROVEEDOR')")
    @GetMapping("/modificarPerfilPublico")
    public String modificarPerfilPublico( ModelMap modelo, HttpSession session) {
        Proveedor proveedor = (Proveedor) session.getAttribute("proveedorsession");

        if (proveedor == null ) {
            return "redirect:/inicio";
        } else {
            PerfilProveedor perfilProveedor= proveedor.getPerfilProveedor();
            modelo.put("proveedor", proveedor);
            modelo.put("perfilProveedor",perfilProveedor);
            modelo.put("imagenes",perfilProveedor.getImagenes());
//            return "PROV_crearPerfilPublico.html";
            return "redirect:/perfilProveedor/verPerfilPublico/" + proveedor.getId();
        }

    }
    
    @PreAuthorize("hasRole('PROVEEDOR')")
    @PostMapping("/modificacionPerfilPublico")
    public String modificacionPerfilPublico( ModelMap modelo,@RequestParam("descripcion") String descripcion,@RequestParam("imagenes") List <MultipartFile> imagenes, HttpSession session) {
        Proveedor proveedor = (Proveedor) session.getAttribute("proveedorsession");

        try{
            perfilProveedorServicio.actualizar(proveedor.getId(), descripcion,imagenes);
            modelo.put("exito", "Perfil actualizado con exito!");
            return "redirect:/perfilProveedor/verPerfilPublico/" + proveedor.getId();
        }catch(Exception ex){
            modelo.put("error", ex.getMessage());
            return "redirect:/perfilProveedor/verPerfilPublico/" + proveedor.getId();
        }

    }
    
    @PostMapping("/eliminarImagen")
    public String eliminarImagen(String imagenId, HttpSession session) throws MiException{
        Proveedor proveedor = (Proveedor) session.getAttribute("proveedorsession");
        perfilProveedorServicio.eliminarImagen(proveedor.getId(), imagenId);
        return "redirect:/perfilProveedor/verPerfilPublico/" + proveedor.getId();
    }
}
