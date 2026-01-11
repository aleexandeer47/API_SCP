package API.APISCP.Repositories.TableIds;

import API.APISCP.Entities.TableIds.TableIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableIdRepository extends JpaRepository <TableIdEntity, Long> {
}
