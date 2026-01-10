package API.APISCP.Entities.Partidas;

import API.APISCP.Entities.Empresas.EmpresasEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "Tb_Partidas")
public class PartidasEntity {

    @Id
    @Column(name = "codigo_p")
    private Long codigo_p;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "unidad_medida")
    private String unidad_medida;
    @Column(name = "cantidad_analizada")
    private BigDecimal cantidad_analizada;
    @Column(name = "produccion_xdia")
    private BigDecimal produccion_xdia;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_e",referencedColumnName = "codigo_e")
    private EmpresasEntity codigo_e;


}
