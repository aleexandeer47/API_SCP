package API.APISCP.Repositories.Materiales;

import API.APISCP.Entities.Materiales.MaterialesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialesRepository extends JpaRepository<MaterialesEntity, Long> {
}
