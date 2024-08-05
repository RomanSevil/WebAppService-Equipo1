package com.WebAppService.Equipo1.repositorios;

import com.WebAppService.Equipo1.entidad.Proveedor;
import com.WebAppService.Equipo1.enums.Profesion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository

public interface ProveedorRepositorio extends JpaRepository<Proveedor, Integer> {


    @Query("SELECT p FROM Proveedor p WHERE p.nombre = :nombre or p.apellido = :apellido")
    public Proveedor buscarPorNombre(@Param("nombre") String nombre, @Param("apellido") String apellido);
    
//    @Query("SELECT p FROM Proveedor p WHERE p.apellido = :apellido")
//    public Proveedor buscarPorApellido(@Param("apellido") String apellido);
    
    @Query("SELECT p FROM Proveedor p WHERE p.email = :email")
    public Proveedor buscarPorEmail(@Param("email") String email);
    
    @Query("SELECT p FROM Proveedor p WHERE p.profesion = :profesion")
    public List<Proveedor> buscarPorProfesion(@Param("profesion") Profesion profesion);

    @Query("SELECT p, p.imagen FROM Proveedor p")
    public List<Object[]> obtenerProveedoresConImagen();

    public boolean existsByEmail(String email);

}

