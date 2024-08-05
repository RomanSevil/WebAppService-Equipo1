package com.WebAppService.Equipo1.servicios;

import com.WebAppService.Equipo1.entidad.Imagen;
import com.WebAppService.Equipo1.entidad.Usuario;
import com.WebAppService.Equipo1.enums.Rol;
import com.WebAppService.Equipo1.excepciones.MiException;
import com.WebAppService.Equipo1.repositorios.UsuarioRepositorio;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.*;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;

    /**
     * Creacion del metodo Crear Usuario, los valores llegan por parametro y los
     * voy seteando y guardando en el repositorio
     *
     * @param nombre
     * @param apellido
     * @param nombreUsuario
     * @param documento
     * @param archivo
     * @param email
     * @param password
     * @param contactoTelefonico
     * @param disponibilidadHoraria
     * @throws MiException
     */
    @Transactional
    public void crearUsuario(String nombre, String apellido, String nombreUsuario, String documento,
            MultipartFile archivo, String email, String password, String contactoTelefonico, String disponibilidadHoraria) throws MiException, IOException {
        if (usuarioRepositorio.existsByEmail(email)) {

            throw new IllegalArgumentException("El email ya está registrado. Por favor, ingrese un email diferente.");
        }

        validar(nombre, apellido, nombreUsuario, documento, email, password, contactoTelefonico, disponibilidadHoraria);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setUsuario(nombreUsuario);
        usuario.setDocumento(documento);
        usuario.setRegistro(Boolean.TRUE);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);
        usuario.setAlta(Boolean.TRUE);
        usuario.setContactoTelefonico(contactoTelefonico);
        usuario.setDisponibilidadHoraria(disponibilidadHoraria);

        Imagen imagen = imagenServicio.guardar(archivo);
        usuario.setImagen(imagen);

        usuarioRepositorio.save(usuario);

    }

    /**
     * Buscando el usuario en la base de datos, si lo encuentra lo iguala a un
     * Objeto Usuario y les setea los nuevos valores
     *
     * @param id
     * @param nombre
     * @param apellido
     * @param nombreUsuario
     * @param documento
     * @param archivo
     * @param email
     * @param password
     * @param contactoTelefonico
     * @param disponibilidadHoraria
     * @throws MiException
     */
    @Transactional

    public void modificarUsuario(Integer id, String nombre, String apellido, String documento,
            MultipartFile archivo, String email, String password, String contactoTelefonico, String disponibilidadHoraria) throws MiException {

        Optional<Usuario> repuesta = usuarioRepositorio.findById(id);

        if (repuesta.isPresent()) {

            Usuario usuario = repuesta.get();

            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            //usuario.setUsuario(nombreUsuario);
            usuario.setDocumento(documento);
            usuario.setEmail(email);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            usuario.setContactoTelefonico(contactoTelefonico);
            usuario.setDisponibilidadHoraria(disponibilidadHoraria);

//            String idImagen = null;
//            if (usuario.getImagen() != null) {
//                idImagen = usuario.getImagen().getId();
//            }
//            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
//            usuario.setImagen(imagen);
            if (archivo != null && !archivo.isEmpty()) {
            Imagen imagen = imagenServicio.guardar(archivo);
            usuario.setImagen(imagen);
        }

            usuarioRepositorio.save(usuario);

        }

    }

    /**
     * Retorna una lista de usuarios
     *
     * @return lista de usuarios
     */
    public List<Usuario> listarUsuarios() {

        List<Usuario> usuarios = new ArrayList();

        usuarios = usuarioRepositorio.findAll();
        List<Imagen> imagenes = new ArrayList();
        imagenes = imagenServicio.listarTodos();

        return usuarios;

    }

    /**
     * Le ingresa, por parametro, el id, lo busca en el repositorio y si lo
     * encuentra le de de baja
     *
     * @param id
     */
    @Transactional
    public void darDeBajaUsuario(Integer id) {

        Optional<Usuario> repuesta = usuarioRepositorio.findById(id);

        if (repuesta.isPresent()) {

            Usuario usuario = repuesta.get();

            usuario.setRegistro(Boolean.FALSE);

            usuarioRepositorio.save(usuario);

        }

    }
    
    @Transactional
    public void darDeAltaUsuario(Integer id) {

        Optional<Usuario> repuesta = usuarioRepositorio.findById(id);

        if (repuesta.isPresent()) {

            Usuario usuario = repuesta.get();

            usuario.setRegistro(Boolean.TRUE);

            usuarioRepositorio.save(usuario);

        }

    }
    
    @Transactional
    public void eliminarUsuario(Integer id) {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setRegistro(Boolean.FALSE);
            usuario.setAlta(Boolean.FALSE);
            usuarioRepositorio.save(usuario);
        }

    }

    /**
     * Valida los datos ingresados, si estan nulos o vacios larga error
     *
     * @param nombre
     * @param apellido
     * @param nombreUsuario
     * @param documento
     * @param archivo
     * @param email
     * @param password
     * @param contactoTelefonico
     * @param disponibilidadHoraria
     * @throws MiException
     */
    public void validar(String nombre, String apellido, String nombreUsuario, String documento,
            String email, String password, String contactoTelefonico, String disponibilidadHoraria) throws MiException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El Nombre no puede estar vacio");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("El Apellido no puede estar vacio");
        }
        if (nombreUsuario.isEmpty() || nombreUsuario == null) {
            throw new MiException("El Nombre Usuario no puede estar vacio");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("El Email no puede estar vacio");
        }
        if (password.isEmpty() || password == null) {
            throw new MiException("La contraseña no puede estar vacia");
        }
        if (contactoTelefonico.isEmpty() || contactoTelefonico == null) {
            throw new MiException("El Contacto Telefonico no puede estar vacio");
        }
        if (disponibilidadHoraria.isEmpty() || disponibilidadHoraria == null) {
            throw new MiException("La Disponibilidad Horaria no puede estar vacio");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

        if (usuario != null && usuario.getRegistro()) {

            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            User user = new User(usuario.getEmail(), usuario.getPassword(), permisos);
            return user;
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }

    public Usuario getOne(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
