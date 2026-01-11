package API.APISCP.Repositories.Partidas;

import API.APISCP.Entities.Partidas.PartidasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartidasRepository extends JpaRepository <PartidasEntity, Long> {
}
