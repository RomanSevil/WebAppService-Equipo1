package com.WebAppService.Equipo1.controladores;

import com.WebAppService.Equipo1.entidad.Comentario;
import com.WebAppService.Equipo1.entidad.Contrato;
import com.WebAppService.Equipo1.entidad.Imagen;
import com.WebAppService.Equipo1.entidad.PerfilProveedor;
import com.WebAppService.Equipo1.entidad.Proveedor;
import com.WebAppService.Equipo1.entidad.Usuario;
import com.WebAppService.Equipo1.enums.Profesion;
import com.WebAppService.Equipo1.enums.Rol;
import com.WebAppService.Equipo1.repositorios.ProveedorRepositorio;
import com.WebAppService.Equipo1.servicios.ComentarioServicio;
import com.WebAppService.Equipo1.servicios.ContratoServicio;
import com.WebAppService.Equipo1.servicios.ProveedorServicio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Roman
 */
@Controller
@RequestMapping("/proveedor")
public class ProveedorControlador {

    @Autowired
    ProveedorServicio proveedorServicio;
    @Autowired
    ContratoServicio contratoServicio;
    @Autowired
    ComentarioServicio comentarioServicio;

    @GetMapping("/lista/{profesion}")
    public String listarProveedoresPorProfesion(ModelMap model, @PathVariable String profesion) {
        Profesion profesionEnum = Profesion.valueOf(profesion.toUpperCase());

        List<Proveedor> proveedores = proveedorServicio.listarProveedoresPorProfesion(profesionEnum);
        model.addAttribute("proveedores", proveedores);

        return "proveedor_list.html";

    }
    
    

    @GetMapping("/lista")
    public String listarProveedores(ModelMap model, @RequestParam String email) {
        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        model.addAttribute("proveedores", proveedores);
        return "proveedor_list.html";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listarProveedoresAdmin")
    public String listarProveedoresAdmin(ModelMap modelo) {

        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        modelo.put("proveedores", proveedores);
        return "ADM_listarProveedores.html";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/darBaja/{id}")
    public String darBaja(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            proveedorServicio.darBaja(id);

            redirectAttributes.addFlashAttribute("exito", "El proveedor ha sido de baja correctamente.");

            return "redirect:/inicio";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/proveedor/listarProveedoresAdmin";
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/darAlta/{id}")
    public String darAlta(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            proveedorServicio.darAlta(id);
            redirectAttributes.addFlashAttribute("exito", "El proveedor ha sido de alta correctamente.");
            return "redirect:/inicio";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/proveedor/listarProveedoresAdmin";
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        proveedorServicio.eliminarProveedor(id);
        redirectAttributes.addFlashAttribute("exito", "El proveedor ha sido eliminado correctamente.");
        return "redirect:/proveedor/listarProveedoresAdmin";
    }

    @GetMapping("/imagen/{id}")
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable Integer id) {
        try {
            Proveedor proveedor = proveedorServicio.getOne(id);
            Imagen imagen = proveedor.getImagen();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imagen.getContenido(), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('PROVEEDOR')")
    @GetMapping("/modificarPerfil/{id}")
    public String modificarPerfil(@PathVariable Integer id, ModelMap modelo, HttpSession session) {
        Proveedor proveedor = (Proveedor) session.getAttribute("proveedorsession");

        if (proveedor == null || !id.equals(proveedor.getId())) {
            return "redirect:/inicio";
        } else {
            modelo.put("proveedor", proveedor);
            return "PROV_modificarPerfil.html";
        }

    }

    //debe ir en modificar en ADM_listarProveedores
    @GetMapping("/modificarPerfilAdmin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String modificarPerfilAdmin(@PathVariable Integer id, ModelMap modelo) {
        modelo.put("proveedor", proveedorServicio.getOne(id));
        return "PROV_modificarPerfil.html";
    }

    @PostMapping("/modificacionPerfil/{id}")
    public String modificacionPerfil(@PathVariable Integer id, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String usuario, @RequestParam String documento, @RequestParam(required = false) MultipartFile archivo, @RequestParam String password, String password2, Profesion profesion, Boolean certificado, String contactoTelefonico, String disponibilidadHoraria,
            Boolean alta, ModelMap modelo, RedirectAttributes redireccion, HttpSession session) throws Exception {

        try {

            proveedorServicio.modificar(id, nombre, apellido, email, password, usuario, documento, archivo, profesion, certificado, contactoTelefonico, disponibilidadHoraria);

            modelo.put("exito", "Proveedor modificado con exito!");
            return "redirect:/inicio";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);

            Usuario logueado = (Usuario) session.getAttribute("usuariosession");
            Proveedor proveedorlogueado = (Proveedor) session.getAttribute("proveedorsession");

            if (logueado != null && logueado.getRol() != null && logueado.getRol().toString().equals("ADMIN")) {
                return "redirect:/modificarPerfil/" + id;
            } else {
                return "redirect:/modificarPerfilAdmin/" + id;
            }
        }

    }

    @GetMapping("/{proveedorId}")
    public String verPerfilPublico(@PathVariable("proveedorId") Integer proveedorId, ModelMap modelo, HttpSession session) {
        Proveedor proveedor = proveedorServicio.getOne(proveedorId);

        if (proveedor == null || !proveedorId.equals(proveedor.getId())) {
            return "redirect:/inicio";
        } else {
            PerfilProveedor perfilProveedor = proveedor.getPerfilProveedor();
            List<Comentario> comentarios = comentarioServicio.listarPorProveedor(proveedorId);
            modelo.put("proveedor", proveedor);
            modelo.put("comentarios", comentarios);

            if (perfilProveedor != null) {
                List<Imagen> imagenes = perfilProveedor.getImagenes();
                modelo.put("perfilProveedor", perfilProveedor);
                modelo.put("imagenes", imagenes);
            } else {
                modelo.put("perfilProveedor", new PerfilProveedor());
                modelo.put("imagenes", Collections.emptyList());
            }
            return "PROV_crearPerfilPublico.html";
        }

    }

}
