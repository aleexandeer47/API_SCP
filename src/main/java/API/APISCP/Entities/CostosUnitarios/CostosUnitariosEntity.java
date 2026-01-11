package API.APISCP.Entities.CostosUnitarios;

import API.APISCP.Entities.CostosIndirectos.CostosIndirectosEntity;
import API.APISCP.Entities.Empresas.EmpresasEntity;
import API.APISCP.Entities.Materiales.MaterialesEntity;
import API.APISCP.Entities.Partidas.PartidasEntity;
import API.APISCP.Entities.PrecioUnitario.PrecioUnitarioEntity;
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
@Table(name = "Tb_Costos_Unitarios")
public class CostosUnitariosEntity {

    @Id
    @Column(name = "codigo_cu")
    private Long codigo_cu;
    @Column(name = "cantidad_material")
    private BigDecimal cantidad_material;
    @Column(name = "rendimiento")
    private BigDecimal rendimiento;
    @Column(name = "calculo_precioU")
    private BigDecimal calculo_precioU;
    @Column(name = "total")
    private BigDecimal total;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_m", referencedColumnName = "codigo_m")
    private MaterialesEntity codigo_m;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_p", referencedColumnName = "codigo_p")
    private PartidasEntity codigo_p;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_ci", referencedColumnName = "codigo_ci")
    private CostosIndirectosEntity codigo_ci;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_e", referencedColumnName = "codigo_e")
    private EmpresasEntity codigo_e;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_pu", referencedColumnName = "codigo_pu")
    private PrecioUnitarioEntity codigo_pu;

}
