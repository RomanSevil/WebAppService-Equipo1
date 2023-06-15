

package com.WebAppService.Equipo1.repositorios;

import com.WebAppService.Equipo1.entidad.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepositorio extends JpaRepository<Proveedor, String>{
    
    @Query("SELECT a FROM Autor a WHERE a.nombre = :nombre")
    public Proveedor buscarPorNombre(@Param("nombre") String nombre);
}





