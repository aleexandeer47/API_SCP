package API.APISCP.Entities.PrecioUnitario;

import API.APISCP.Entities.Empresas.EmpresasEntity;
import API.APISCP.Entities.Materiales.MaterialesEntity;
import API.APISCP.Entities.UnidadesMedida.UnidadesMedidaEntity;
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
@Table(name = "Tb_Precios_Unitarios")
public class PrecioUnitarioEntity {

    @Id
    @Column(name = "codigo_pu")
    private Long codigo_pu;
    @Column(name = "precio")
    private BigDecimal precio;
    @Column(name = "presentacion")
    private BigDecimal presentacion;
    @Column(name = "precio_unitario")
    private BigDecimal precio_unitario;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_m", referencedColumnName = "codigo_m")
    private MaterialesEntity codigo_m;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_u", referencedColumnName = "codigo_u")
    private UnidadesMedidaEntity codigo_u;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_e", referencedColumnName = "codigo_e")
    private EmpresasEntity codigo_e;

}
