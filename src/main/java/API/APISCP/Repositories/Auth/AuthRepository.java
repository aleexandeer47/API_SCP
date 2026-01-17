package API.APISCP.Repositories.Auth;

import API.APISCP.Entities.Usuarios.UsuariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository <UsuariosEntity, Long> {

    Optional<UsuariosEntity> findByUsuario(String usuario);
}
