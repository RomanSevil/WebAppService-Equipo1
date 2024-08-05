package com.WebAppService.Equipo1.repositorios;

import com.WebAppService.Equipo1.entidad.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String> {
}
