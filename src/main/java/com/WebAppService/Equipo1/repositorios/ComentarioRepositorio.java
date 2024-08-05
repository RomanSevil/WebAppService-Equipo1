
package com.WebAppService.Equipo1.repositorios;

import com.WebAppService.Equipo1.entidad.Comentario;
import com.WebAppService.Equipo1.enums.EstadoComentario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, Integer>{
    
    @Query("SELECT c FROM Comentario c WHERE c.proveedor.id = :proveedorId ORDER BY c.id DESC")
    public List<Comentario> buscarPorProveedor(@Param("proveedorId") Integer proveedorId);
    
    @Query("SELECT c FROM Comentario c WHERE c.contrato.usuario.id = :usuarioId ORDER BY c.id DESC")
    public List<Comentario> buscarPorUsuario(@Param("usuarioId") Integer usuarioId);
    
    @Query("SELECT c FROM Comentario c WHERE c.estado = :estado ORDER BY RAND()")
    public List<Comentario> buscarPorEstado(@Param("estado") EstadoComentario estado);
    
    
}
