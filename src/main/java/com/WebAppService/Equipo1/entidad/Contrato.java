package com.WebAppService.Equipo1.entidad;

import com.WebAppService.Equipo1.enums.Estado;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    private Date fechaDeFirma;
    @Temporal(TemporalType.DATE)
    private Date fechaCulminacion;
    private String horarioPreferenteFirma;
    private String descripcionDelContrato;
    private String direccionDelContrato;
    private String barrio;
    private Integer calificacion;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;
    @Enumerated(EnumType.STRING)
    private Estado estado;

}
