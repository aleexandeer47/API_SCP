package API.APISCP.Entities.Empresas;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "Tb_Empresas")
public class EmpresasEntity {

    @Id
    @Column(name = "codigo_e")
    private Long codigo_e;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "nit")
    private String nit;
}
