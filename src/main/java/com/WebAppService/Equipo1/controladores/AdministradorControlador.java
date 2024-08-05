/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.WebAppService.Equipo1.controladores;

import com.WebAppService.Equipo1.entidad.*;
import com.WebAppService.Equipo1.servicios.*;
import com.WebAppService.Equipo1.entidad.*;
import com.WebAppService.Equipo1.servicios.*;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author usuario
 */
@Controller
@RequestMapping("/admin")
public class AdministradorControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private AdministradorServicio administradorServicio;
    @Autowired
    private ProveedorServicio proveedorServicio;
    @Autowired
    private ComentarioServicio comentarioServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "ADM_dashboard.html";
    }

    @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "ADM_listarUsuarios.html";
    }
    
    @GetMapping("/proveedores")
    public String listarProveedores(ModelMap modelo) {
        List<Proveedor> proveedores = proveedorServicio.listarProveedores();
        modelo.addAttribute("proveedores", proveedores);
        return "ADM_listarProveedores.html";
    }
    
    @GetMapping("/comentarios")
    public String listarComentarios(ModelMap modelo) {
        List<Comentario> comentarios = comentarioServicio.listarComentarios();
//         List<Comentario> comentariosOrdenados= comentarios.stream().sorted((c1,c2)-> c2.getId().compareTo(c1.getId())).collect(Collectors.toList());
        //List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("comentarios", comentarios);
        //modelo.addAttribute("usuarios", usuarios);
        return "ADM_listarComentarios.html";
    }

    

    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable Integer id) {
        administradorServicio.cambiarRol(id);

        return "redirect:/admin/usuarios";
    }
  
    
}
