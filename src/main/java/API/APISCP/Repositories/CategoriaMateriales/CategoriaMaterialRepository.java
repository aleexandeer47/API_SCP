package API.APISCP.Repositories.CategoriaMateriales;

import API.APISCP.Entities.CategoriaMateriales.CategoriaMaterialesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaMaterialRepository extends JpaRepository<CategoriaMaterialesEntity, Long> {

}
