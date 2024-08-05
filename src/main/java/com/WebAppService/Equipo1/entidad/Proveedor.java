package com.WebAppService.Equipo1.entidad;

import com.WebAppService.Equipo1.enums.*;
import javax.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
public class Proveedor extends Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @OneToOne
    private Imagen imagen;
    private Double calificacion;
    @Enumerated(EnumType.STRING)
    private Profesion profesion;
    private Boolean certificado;
    private String contactoTelefonico;
    private String disponibilidadHoraria;
    private Boolean alta;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @OneToMany(mappedBy = "proveedor")
    private List<Contrato> contratos;
    @OneToOne(mappedBy = "proveedor")
    private PerfilProveedor perfilProveedor;

}
