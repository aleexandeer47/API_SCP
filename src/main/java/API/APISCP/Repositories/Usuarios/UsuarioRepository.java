package API.APISCP.Repositories.Usuarios;

import API.APISCP.Entities.Usuarios.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuariosEntity, Long> {
}
