package API.APISCP.Repositories.Empresas;

import API.APISCP.Entities.Empresas.EmpresasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresasRepository extends JpaRepository<EmpresasEntity, Long> {
}
