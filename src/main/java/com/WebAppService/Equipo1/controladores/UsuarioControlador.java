/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.WebAppService.Equipo1.controladores;

import com.WebAppService.Equipo1.entidad.Imagen;

import java.util.List;

import com.WebAppService.Equipo1.entidad.Usuario;
import com.WebAppService.Equipo1.enums.Rol;
import com.WebAppService.Equipo1.repositorios.UsuarioRepositorio;
import com.WebAppService.Equipo1.servicios.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Roman
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/modificarPerfil/{id}")
    public String modificarPerfil(@PathVariable Integer id, ModelMap modelo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        if (usuario == null || !id.equals(usuario.getId())) {
            return "inicio.html";
        } else {
            modelo.put("usuario", usuario);
            return "modificar_perfil_usuario.html";
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/modificarPerfilAdmin/{id}")
    public String modificarPerfilAdmin(@PathVariable Integer id, ModelMap modelo) {
        modelo.put("usuario", usuarioRepositorio.getOne(id));
        return "modificar_perfil_usuario.html";

    }

    @PostMapping("/modificacionPerfil/{id}")
    public String modificacionPerfil(@PathVariable("id") Integer id, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String documento, @RequestParam(required = false) MultipartFile archivo,
            @RequestParam(required = false) String password, @RequestParam(required = false) String password2, @RequestParam Rol rol, @RequestParam(required = false) String contactoTelefonico, String disponibilidadHoraria,
            Boolean alta, ModelMap modelo, RedirectAttributes redireccion) throws Exception {

        try {
            System.out.println("ID: " + id);
            System.out.println("Nombre: " + nombre);
            System.out.println("Apellido: " + apellido);
            usuarioServicio.modificarUsuario(id, nombre, apellido, documento, archivo, email, password, contactoTelefonico, disponibilidadHoraria);
            modelo.put("exito", "usuario modificado con exito");
            return "redirect:/inicio";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:/modificarPerfil/" + id;
        }

    }

    @GetMapping("/listarUsuarios")
    public String listarUsuarios(ModelMap modelo) {

        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.put("usuarios", usuarios);
        return "ADM_listarUsuarios.html";
    }
    
    @PreAuthorize("hasRole('ADMIN')")   
    @GetMapping("/darDeBaja/{id}")
    public String darDeBaja(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            usuarioServicio.darDeBajaUsuario(id);
            redirectAttributes.addFlashAttribute("exito", "El usuario ha sido dado de baja correctamente.");
            return "redirect:/inicio";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/usuario/listarUsuarios";
        }

    }
    
    @PreAuthorize("hasRole('ADMIN')")   
    @GetMapping("/darDeAlta/{id}")
    public String darDeAlta(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            usuarioServicio.darDeAltaUsuario(id);
            redirectAttributes.addFlashAttribute("exito", "El usuario ha sido dado de alta correctamente.");
            return "redirect:/inicio";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/usuario/listarUsuarios";
        }

    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {       
            usuarioServicio.eliminarUsuario(id);
            redirectAttributes.addFlashAttribute("exito", "El usuario ha sido eliminado correctamente.");
            return "redirect:/usuario/listarUsuarios"; 
    }

    @GetMapping("/imagen/{id}")
    public ResponseEntity<byte[]> obtenerImagen(@PathVariable Integer id) {
        try {
            Usuario usuario = usuarioRepositorio.getOne(id);
            Imagen imagen = usuario.getImagen();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imagen.getContenido(), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
