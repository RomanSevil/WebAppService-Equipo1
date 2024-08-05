/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.WebAppService.Equipo1.servicios;

import com.WebAppService.Equipo1.entidad.Imagen;
import com.WebAppService.Equipo1.entidad.PerfilProveedor;
import com.WebAppService.Equipo1.entidad.Proveedor;
import com.WebAppService.Equipo1.excepciones.MiException;
import com.WebAppService.Equipo1.repositorios.PerfilProveedorRepositorio;
import com.WebAppService.Equipo1.repositorios.ProveedorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PerfilProveedorServicio {

    @Autowired
    private PerfilProveedorRepositorio perfilProveedorRepositorio;

    @Autowired
    private ProveedorRepositorio proveedorRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void crearPerfilPublico(Integer proveedorId, String descripcion, List<MultipartFile> imagenes) throws MiException {
        Proveedor proveedor = proveedorRepositorio.findById(proveedorId)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado con el ID: " + proveedorId));

        PerfilProveedor perfilProveedor = new PerfilProveedor();
        perfilProveedor.setProveedor(proveedor);
        perfilProveedor.setDescripcion(descripcion);

        List<Imagen> imagenesGuardadas = new ArrayList<>();
        for (MultipartFile archivo : imagenes) {
            Imagen imagen = imagenServicio.guardar(archivo);
            imagenesGuardadas.add(imagen);
        }
        perfilProveedor.setImagenes(imagenesGuardadas);

        perfilProveedorRepositorio.save(perfilProveedor);
    }

    @Transactional
    public void actualizar(Integer proveedorId, String descripcion, List<MultipartFile> imagenes) throws MiException {

        PerfilProveedor perfilProveedor = perfilProveedorRepositorio.buscarPorProveedor(proveedorId);
        if (perfilProveedor != null) {
            perfilProveedor.setDescripcion(descripcion);

            List<Imagen> imagenesGuardadas = perfilProveedor.getImagenes();
            if (imagenes != null && imagenes.size() > 0) {
                for (MultipartFile archivo : imagenes) {
                    if (archivo != null && !archivo.isEmpty()) {
                        Imagen imagen = imagenServicio.guardar(archivo);
                        if (imagen != null) {
                            imagenesGuardadas.add(imagen);
                        }
                    }
                }
            }

            perfilProveedor.setImagenes(imagenesGuardadas);

            perfilProveedorRepositorio.save(perfilProveedor);
        }

    }

    @Transactional
    public void eliminarImagen(Integer proveedorId, String imagenId) throws MiException {
        PerfilProveedor perfilProveedor = perfilProveedorRepositorio.buscarPorProveedor(proveedorId);
        List<Imagen> imagenesGuardadas = perfilProveedor.getImagenes();
        imagenesGuardadas.removeIf(imagen -> imagen.getId().equals(imagenId));
        perfilProveedor.setImagenes(imagenesGuardadas);
        perfilProveedorRepositorio.save(perfilProveedor);
    }
}
