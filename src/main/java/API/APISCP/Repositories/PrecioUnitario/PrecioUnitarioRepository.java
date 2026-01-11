package API.APISCP.Repositories.PrecioUnitario;

import API.APISCP.Entities.PrecioUnitario.PrecioUnitarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecioUnitarioRepository extends JpaRepository<PrecioUnitarioEntity, Long> {
}
