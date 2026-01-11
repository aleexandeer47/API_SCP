package API.APISCP.Repositories.CostosUnitarios;

import API.APISCP.Entities.CostosUnitarios.CostosUnitariosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostosUnitariosRepository extends JpaRepository<CostosUnitariosEntity, Long> {
}
