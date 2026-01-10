package API.APISCP.Entities.Materiales;

import API.APISCP.Entities.CategoriaMateriales.CategoriaMaterialesEntity;
import API.APISCP.Entities.Empresas.EmpresasEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "Tb_Materiales")
public class MaterialesEntity {

    @Id
    @Column(name = "codigo_m")
    private Long codigo_m;
    @Column(name = "descripcion")
    private String descripcion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_cm", referencedColumnName = "codigo_cm")
    private CategoriaMaterialesEntity codigo_cm;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_e", referencedColumnName = "codigo_e")
    private EmpresasEntity codigo_e;

}
