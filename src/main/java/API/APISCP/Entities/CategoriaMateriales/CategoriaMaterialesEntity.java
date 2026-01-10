package API.APISCP.Entities.CategoriaMateriales;


import API.APISCP.Entities.Empresas.EmpresasEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "Tb_Categorias_Materiales")
public class CategoriaMaterialesEntity {

@Id
@Column(name = "codigo_cm")
private  Long codigo_cm;
@Column(name = "descripcion")
private String descripcion;
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "codigo_e" , referencedColumnName = "codigo_e")
private EmpresasEntity codigo_e;

}
