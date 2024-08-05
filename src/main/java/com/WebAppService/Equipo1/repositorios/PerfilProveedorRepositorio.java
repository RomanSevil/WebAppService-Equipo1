/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.WebAppService.Equipo1.repositorios;

import com.WebAppService.Equipo1.entidad.PerfilProveedor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilProveedorRepositorio extends JpaRepository<PerfilProveedor, Integer>{
    @Query("SELECT p FROM PerfilProveedor p WHERE p.proveedor.id = :proveedorId")
    public PerfilProveedor buscarPorProveedor(@Param("proveedorId") Integer proveedorId);
}
