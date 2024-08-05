package com.WebAppService.Equipo1.repositorios;

import com.WebAppService.Equipo1.entidad.Contrato;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoRepositorio extends JpaRepository<Contrato, Integer> {

    @Query("SELECT c FROM Contrato c WHERE c.usuario.id = :usuarioId ORDER BY c.id DESC")
    public List<Contrato> buscarPorUsuario(@Param("usuarioId") Integer usuarioId);
    
    @Query("SELECT c FROM Contrato c WHERE c.proveedor.id = :proveedorId ORDER BY c.id DESC")
    public List<Contrato> buscarPorProveedor(@Param("proveedorId") Integer proveedorId);

}

