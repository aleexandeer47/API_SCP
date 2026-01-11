package API.APISCP.Repositories.CostosIndirectos;

import API.APISCP.Entities.CostosIndirectos.CostosIndirectosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostosIndirectosRepository extends JpaRepository<CostosIndirectosEntity, Long> {
}
