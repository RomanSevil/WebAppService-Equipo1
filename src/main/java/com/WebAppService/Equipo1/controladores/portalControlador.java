/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.WebAppService.Equipo1.controladores;

import com.WebAppService.Equipo1.entidad.*;
import com.WebAppService.Equipo1.enums.*;
import com.WebAppService.Equipo1.repositorios.*;
import com.WebAppService.Equipo1.servicios.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author usuario
 */
@Controller
@RequestMapping("/")
public class portalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ProveedorServicio proveedorServicio;

    @Autowired
    private ContratoServicio contratoServicio;

    @Autowired
    private ComentarioServicio comentarioServicio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private AdministradorRepositorio administradorRepositorio;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_PROVEEDOR')")
    @GetMapping("/inicio")
    public String index(HttpSession session, ModelMap modelo) {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        Proveedor proveedorlogueado = (Proveedor) session.getAttribute("proveedorsession");

        if (logueado != null && logueado.getRol() != null && logueado.getRol().toString().equals("ADMIN")) {
            return "ADM_dashboard.html";
        } else if (proveedorlogueado != null && proveedorlogueado.getRol() != null && proveedorlogueado.getRol().toString().equals("PROVEEDOR")) {
            List<Contrato> contratos = contratoServicio.listarContratosProveedor(proveedorlogueado.getId());
            List<Comentario> comentarios = comentarioServicio.listarPorProveedor(proveedorlogueado.getId());
            modelo.put("contratos", contratos);
            modelo.put("proveedor", proveedorlogueado);
            modelo.put("comentarios", comentarios);
            return "PROV_panelProveedor.html";
        } else {
            if (logueado != null) {
                List<Contrato> contratos = contratoServicio.listarContratosUsuario(logueado.getId());
                modelo.put("contratos", contratos);
            }
            return "inicio.html";
        }
    }

    @GetMapping("/")
    public String index(ModelMap model) {
        EstadoComentario estadoEnum = EstadoComentario.DESTACADO;
        List<Comentario> comentarios = comentarioServicio.listarPorEstado(estadoEnum);
        List <Map<String,String>> suggestions = proveedorServicio.mostrarSugerencias();
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("comentarios", comentarios);
        return "index.html";
    }

    @GetMapping("/registrarUsuario")
    public String registrar() {
        return "registroUsuario.html";
    }

    @PostMapping("/registroUsuario")
    public String registro(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String documento, MultipartFile archivo, @RequestParam String password, String password2, @RequestParam Rol rol, String usuario, Double calificacion,
            Boolean certificado, String contactoTelefonico, String disponibilidadHoraria,
            Boolean alta, ModelMap modelo, RedirectAttributes redireccion) throws Exception {
        try {
            usuarioServicio.crearUsuario(nombre, apellido, usuario, documento, archivo, email, password, contactoTelefonico, disponibilidadHoraria);
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            return "registroUsuario";
        }
        modelo.put("exito", "has sido registrado con exito!");
        return "index.html";
    }

    @GetMapping("/registrarProveedor")
    public String registrarProveedor() {
        return "PROV_registroProveedor";
    }

    @PostMapping("/registroProveedor")
    public String registroProveedor(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String documento, MultipartFile archivo, @RequestParam String password, String password2, @RequestParam Rol rol, String usuario, Double calificacion, Profesion profesion,
            Boolean certificado, String contactoTelefonico, String disponibilidadHoraria,
            Boolean alta, ModelMap modelo, RedirectAttributes redireccion) throws Exception {
        try {

            proveedorServicio.registrar(nombre, apellido, email, password, usuario, documento, archivo, profesion, certificado, contactoTelefonico, disponibilidadHoraria);

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);

            return "PROV_registroProveedor";
        }
        modelo.put("exito", "has sido registrado con exito!");
        return "index.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o contraseñas invalidos");

        }
        return "login.html";
    }

}

/*
if (logueado != null && logueado.getRol() != null && logueado.getRol().toString().equals("ADMIN")) {
            return "ADM_dashboard.html";
        } else if (proveedorlogueado != null && proveedorlogueado.getRol() != null && proveedorlogueado.getRol().toString().equals("PROVEEDOR")) {
            List<Contrato> contratos = contratoServicio.listarContratosProveedor(proveedorlogueado.getId());
            List<Contrato> contratosOrdenados= contratos.stream().sorted((c1,c2)-> c2.getId().compareTo(c1.getId())).collect(Collectors.toList());
            List<Comentario> comentarios= comentarioServicio.listarPorProveedor(proveedorlogueado.getId());
            List<Comentario> comentariosOrdenados= comentarios.stream().sorted((c1,c2)-> c2.getId().compareTo(c1.getId())).collect(Collectors.toList());
            modelo.put("contratos", contratosOrdenados);
            modelo.put("proveedor", proveedorlogueado);
            modelo.put("comentarios",comentariosOrdenados);
            return "PROV_panelProveedor.html";
        } else {
            if (logueado != null) {
                List<Contrato> contratos = contratoServicio.listarContratosUsuario(logueado.getId());
                List<Contrato> contratosOrdenados= contratos.stream().sorted((c1,c2)-> c2.getId().compareTo(c1.getId())).collect(Collectors.toList());
                modelo.put("contratos", contratosOrdenados);
            }
            return "inicio.html";
        }
*/
