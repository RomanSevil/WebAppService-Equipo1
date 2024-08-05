package com.WebAppService.Equipo1.entidad;

import lombok.*;
import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    protected String nombre;
    protected String apellido;
    protected String email;
    protected String password;
    protected String usuario;
    protected String documento;
    protected Boolean registro;

}
