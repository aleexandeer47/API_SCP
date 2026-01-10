package API.APISCP.Entities.CostosIndirectos;

import API.APISCP.Entities.Empresas.EmpresasEntity;
import API.APISCP.Entities.Materiales.MaterialesEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "Tb_Costos_Indirectos")
public class CostosIndirectosEntity {

    @Id
    @Column(name = "codigo_ci")
    private Long codigo_ci;
    @Column(name = "precio")
    private BigDecimal precio;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_m" , referencedColumnName = "codigo_m")
    private MaterialesEntity codigo_m;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_e" , referencedColumnName = "codigo_e")
    private EmpresasEntity codigo_e;


}
