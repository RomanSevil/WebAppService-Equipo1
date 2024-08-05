/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.WebAppService.Equipo1.repositorios;

import com.WebAppService.Equipo1.entidad.Administrador;
import com.WebAppService.Equipo1.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author usuario
 */
@Repository
public interface AdministradorRepositorio extends JpaRepository <Administrador, Integer>{
    
   @Query("SELECT a FROM Administrador a WHERE a.nombre = :nombre")
    public Administrador buscarPorNombre(@Param("nombre") String nombre);
    
//    @Query("SELECT a FROM Administrador a WHERE a.email = :email")
//    public Administrador buscarPorEmail(@Param("email") String email);
}
