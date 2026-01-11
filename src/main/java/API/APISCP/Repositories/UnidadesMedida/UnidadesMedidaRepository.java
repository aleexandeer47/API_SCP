package API.APISCP.Repositories.UnidadesMedida;

import API.APISCP.Entities.UnidadesMedida.UnidadesMedidaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadesMedidaRepository extends JpaRepository<UnidadesMedidaEntity, Long> {
}
