package com.WebAppService.Equipo1.entidad;

import com.WebAppService.Equipo1.enums.Rol;
import javax.persistence.*;
import lombok.*;
import java.util.List;


@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Usuario extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    
    @OneToOne
    private Imagen imagen;
    
    private String contactoTelefonico;
    private String disponibilidadHoraria;
    private Boolean alta;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    
    @OneToMany(mappedBy = "usuario")
    private List<Contrato> contratos;

    
}
