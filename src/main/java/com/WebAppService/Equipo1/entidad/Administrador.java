/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.WebAppService.Equipo1.entidad;

/**
 *
 * @author usuario
 */
import com.WebAppService.Equipo1.enums.Rol;
import lombok.*;
import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Administrador extends Persona {
     @OneToOne
    private Imagen imagen;
      @Enumerated(EnumType.STRING)
    private Rol rol;
    private String password;
}
