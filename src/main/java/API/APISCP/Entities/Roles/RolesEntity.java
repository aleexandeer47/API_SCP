package API.APISCP.Entities.Roles;

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
@Table(name = "Tb_Roles")
public class RolesEntity {

    @Id
    @Column(name = "codigo_rol")
    private Long codigo_rol;
    @Column(name = "rol")
    private String rol;



}
