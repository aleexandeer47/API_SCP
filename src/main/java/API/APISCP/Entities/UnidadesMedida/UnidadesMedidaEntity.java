package API.APISCP.Entities.UnidadesMedida;

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
@Table(name = "Tb_Unidades_Medida")
public class UnidadesMedidaEntity {

    @Id
    @Column(name = "codigo_u")
    private Long codigo_u;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "abreviatura")
    private String abreviatura;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_e" , referencedColumnName =  "codigo_e")
    private EmpresasEntity codigo_e;


}
