package com.WebAppService.Equipo1.servicios;

import com.WebAppService.Equipo1.entidad.*;
import com.WebAppService.Equipo1.enums.*;
import com.WebAppService.Equipo1.excepciones.MiException;
import com.WebAppService.Equipo1.repositorios.ContratoRepositorio;
import com.WebAppService.Equipo1.repositorios.ProveedorRepositorio;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class ProveedorServicio implements UserDetailsService {

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;
    @Autowired
    private ContratoRepositorio contratoRepositorio;

    @Transactional
    public void registrar(String nombre, String apellido, String email, String password, String usuario, String documento,
            MultipartFile archivo, Profesion profesion, Boolean certificado,
            String contactoTelefonico, String disponibilidadHoraria) throws MiException, URISyntaxException {
        if (proveedorRepositorio.existsByEmail(email)) {
            throw new IllegalArgumentException("El email ya está registrado. Por favor, ingrese un email diferente.");
        }
        Proveedor proveedor = new Proveedor();

        proveedor.setNombre(nombre);
        proveedor.setApellido(apellido);
        proveedor.setEmail(email);
        proveedor.setPassword(new BCryptPasswordEncoder().encode(password));
        proveedor.setUsuario(usuario);
        proveedor.setDocumento(documento);
        proveedor.setRegistro(Boolean.TRUE);
        proveedor.setCalificacion(0.0);
        proveedor.setProfesion(profesion);
        proveedor.setCertificado(certificado);
        proveedor.setContactoTelefonico(contactoTelefonico);
        proveedor.setDisponibilidadHoraria(disponibilidadHoraria);
        proveedor.setAlta(true);
        proveedor.setRol(Rol.PROVEEDOR);
        Imagen imagen = imagenServicio.guardar(archivo);
        proveedor.setImagen(imagen);

        proveedorRepositorio.save(proveedor);
    }

    @Transactional
    public void modificar(Integer id, String nombre, String apellido, String correo, String password, String usuario, String documento,
            MultipartFile archivo, Profesion profesion, Boolean certificado,
            String contactoTelefonico, String disponibilidadHoraria) throws MiException {

        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);

        Proveedor proveedor = respuesta.get();

        proveedor.setNombre(nombre);
        proveedor.setApellido(apellido);
        proveedor.setEmail(correo);
        proveedor.setPassword(new BCryptPasswordEncoder().encode(password));
        proveedor.setUsuario(usuario);
        proveedor.setDocumento(documento);
        proveedor.setRegistro(Boolean.TRUE);
        proveedor.setProfesion(profesion);
        proveedor.setCertificado(certificado);
        proveedor.setContactoTelefonico(contactoTelefonico);
        proveedor.setDisponibilidadHoraria(disponibilidadHoraria);
        proveedor.setAlta(true);
        proveedor.setRol(Rol.PROVEEDOR);

        if (archivo != null && !archivo.isEmpty()) {
            Imagen imagen = imagenServicio.guardar(archivo);
            proveedor.setImagen(imagen);
        }

        proveedorRepositorio.save(proveedor);
    }

    @Transactional
    public void calificacion(Integer id) {
        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);
        Proveedor proveedor = respuesta.get();
        List<Contrato> contratos = contratoRepositorio.buscarPorProveedor(id);

        if (contratos.isEmpty()) {
            proveedor.setCalificacion(0.0);
        }

        Double sumaCalificaciones = 0.0;
        for (Contrato contrato : contratos) {
            sumaCalificaciones += contrato.getCalificacion();
        }

        Double promedio = sumaCalificaciones / contratos.size();
        promedio = Math.round(promedio * 100.0) / 100.0;
        System.out.println("******** " + promedio);
        proveedor.setCalificacion(promedio);

        proveedorRepositorio.save(proveedor);

    }

    @Transactional
    public void eliminarProveedor(Integer id) {

        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Proveedor proveedor = respuesta.get();
            proveedor.setRegistro(Boolean.FALSE);
            proveedor.setAlta(Boolean.FALSE);
            proveedorRepositorio.save(proveedor);
        }

    }

    @Transactional
    public void darBaja(Integer id) {

        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Proveedor proveedor = respuesta.get();
            proveedor.setRegistro(Boolean.FALSE);
            proveedorRepositorio.save(proveedor);
        }

    }

    @Transactional
    public void darAlta(Integer id) {

        Optional<Proveedor> respuesta = proveedorRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Proveedor proveedor = respuesta.get();
            proveedor.setRegistro(Boolean.TRUE);
            proveedorRepositorio.save(proveedor);
        }

    }

    public List<Proveedor> listarProveedores() {
        List<Proveedor> proveedoresPorEmail = new ArrayList();
        proveedoresPorEmail = proveedorRepositorio.findAll();
        List<Imagen> imagenes = new ArrayList();
        imagenes = imagenServicio.listarTodos();
        return proveedoresPorEmail;
    }

    public List<Proveedor> listarProveedoresPorProfesion(Profesion profesion) {
        List<Proveedor> proveedores = new ArrayList<>();
        proveedores = (List<Proveedor>) proveedorRepositorio.buscarPorProfesion(profesion);
        return proveedores;
    }
    
    public List<Map<String,String>> mostrarSugerencias() {
        
        List<Proveedor> proveedores = proveedorRepositorio.findAll();
        List<Map<String,String>> sugerencias = proveedores.stream()
                .map(p -> {
                    Map<String,String> sugerencia = new HashMap<>();
                    sugerencia.put("profesion", p.getProfesion().name());
                    sugerencia.put("nombre", p.getNombre());
                    sugerencia.put("apellido", p.getApellido());
                    sugerencia.put("id", p.getId().toString());
                    return sugerencia;
                }).collect(Collectors.toList());
        
        return sugerencias;
    }

//    public List<String> mostrarSugerencias() {
//        List<String> sugerencias = new ArrayList<>();
//        List<Proveedor> proveedores = proveedorRepositorio.findAll();
//        for (Proveedor proveedor : proveedores) {
//            sugerencias.add("Profesión: " + proveedor.getProfesion().name());
//            sugerencias.add("Nombre: " + proveedor.getNombre());
//            sugerencias.add("Apellido: " + proveedor.getApellido());
//        }
//        return sugerencias;
//    }

//    public List<Proveedor> buscarProveedores(String profesion,String email,String nombre,String apellido) {
//        List<Proveedor> proveedores = new ArrayList();
//        if (nombre != null && !nombre.isEmpty() || apellido != null && !apellido.isEmpty()) {
//            proveedores.addAll((Collection<? extends Proveedor>) proveedorRepositorio.buscarPorNombre(nombre,apellido));
//        }
//        if (email != null && !email.isEmpty()) {
//            proveedores.addAll((Collection<? extends Proveedor>) proveedorRepositorio.buscarPorEmail(email));
//        }
//        if (profesion != null) {
//            Profesion profesionEnum = Profesion.valueOf(profesion.toUpperCase());
//            proveedores.addAll(proveedorRepositorio.buscarPorProfesion(profesionEnum));
//        }
//        return proveedores;
//    }
//    public List<Proveedor> listarProveedoresEmail(String email) {
//        List<Proveedor> proveedores = new ArrayList();
//        proveedores = (List<Proveedor>) proveedorRepositorio.buscarPorEmail(email);      
//        return proveedores;
//    }
//    
//    public List<Proveedor> listarProveedoresNombre(String nombre,String apellido) {
//        List<Proveedor> proveedores = new ArrayList();
//        proveedores = (List<Proveedor>) proveedorRepositorio.buscarPorNombre(nombre);      
//        return proveedores;
//    }
    public Proveedor getOne(Integer id) {
        return proveedorRepositorio.getOne(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Proveedor proveedor = proveedorRepositorio.buscarPorEmail(email);
        System.out.println("***************Proveedor " + proveedor.getEmail());

        if (proveedor != null && proveedor.getRegistro()) {

            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + proveedor.getRol().toString());
            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("proveedorsession", proveedor);

            User user = new User(proveedor.getEmail(), proveedor.getPassword(), permisos);
            return user;
        } else {
            throw new UsernameNotFoundException("Proveedor no encontrado");
        }
    }

}
