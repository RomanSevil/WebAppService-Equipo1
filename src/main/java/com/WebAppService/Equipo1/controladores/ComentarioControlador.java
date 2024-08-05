package com.WebAppService.Equipo1.controladores;

import com.WebAppService.Equipo1.entidad.Comentario;
import com.WebAppService.Equipo1.entidad.Usuario;
import com.WebAppService.Equipo1.servicios.ComentarioServicio;
import com.WebAppService.Equipo1.servicios.ContratoServicio;
import com.WebAppService.Equipo1.servicios.UsuarioServicio;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_PROVEEDOR')")
@Controller
@RequestMapping("/comentarios")
public class ComentarioControlador {

    @Autowired
    private ComentarioServicio comentarioServicio;

  
    
    @GetMapping("/listarComentarios")
    public String listarComentarios(ModelMap modelo) {
        List<Comentario> comentarios = comentarioServicio.listarComentarios();
         List<Comentario> comentariosOrdenados= comentarios.stream().sorted((c1,c2)-> c2.getId().compareTo(c1.getId())).collect(Collectors.toList());
        modelo.addAttribute("comentarios", comentariosOrdenados);
        
        return "ADM_listarComentarios.html";
    }
    
    @GetMapping("/denunciar/{id}")
    public String denunciar(@PathVariable Integer id,ModelMap model) {
        comentarioServicio.denunciarComentario(id);
        model.put("mensaje", "Comentario denunciado");
        return "redirect:/inicio";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/censurar/{id}")
    public String censurar(@PathVariable Integer id,ModelMap model) {
        comentarioServicio.censurarComentario(id);
        model.put("mensaje", "Comentario censurado");
        return "redirect:/comentarios/listarComentarios";
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/destacar/{id}")
    public String destacar(@PathVariable Integer id,ModelMap model) {
        comentarioServicio.destacarComentario(id);
        model.put("mensaje", "Comentario destacado");
        return "redirect:/comentarios/listarComentarios";
    }

}
