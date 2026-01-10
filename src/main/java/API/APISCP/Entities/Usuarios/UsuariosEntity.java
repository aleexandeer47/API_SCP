package API.APISCP.Entities.Usuarios;

import API.APISCP.Entities.Empresas.EmpresasEntity;
import API.APISCP.Entities.Roles.RolesEntity;
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
@Table(name = "Tb_Usuarios")
public class UsuariosEntity {

    @Id
    @Column(name = "codigo_usuario")
    private Long codigo_usuario;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "contrasena")
    private String contrasena;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_rol", referencedColumnName = "codigo_rol")
    private RolesEntity codigo_rol;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_e", referencedColumnName = "codigo_e")
    private EmpresasEntity codigo_e;
}
