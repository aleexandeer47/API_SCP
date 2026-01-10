package API.APISCP.Entities.TableIds;

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
@ToString
@EqualsAndHashCode
@Table(name = "Tb_Id")
public class TableIdEntity {

    @Id
    @Column(name = "codigo_tabla")
    private Long codigo_tabla;
    @Column(name = "nombre_tabla")
    private String nombre_tabla;
    @Column(name = "correlativo")
    private Long correlativo;

}
