
package com.WebAppService.Equipo1.repositorios;

import com.WebAppService.Equipo1.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer>{
    
    @Query("SELECT a FROM Autor a WHERE a.nombre = :nombre")
    public Usuario buscarPorNombre(@Param("nombre") String nombre);
}