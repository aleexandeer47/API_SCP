package API.APISCP.Services.Roles;

import API.APISCP.Entities.Roles.RolesEntity;
import API.APISCP.Exceptions.Roles.RolNoEncontrado;
import API.APISCP.Models.DTO.Roles.RolesDTO;
import API.APISCP.Repositories.Roles.RolesRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleService {

    @Autowired
    private RolesRepository repo;

    /**
     *
     * @return
     */
    public List<RolesDTO>obtenerRol(){
        List<RolesEntity> entityList = repo.findAll();
        return entityList.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param jsonDTO
     * @return
     */
    public RolesDTO insertar(@Valid RolesDTO jsonDTO){
        if (jsonDTO == null){
            throw new IllegalArgumentException("El valor del rol no puede ser nulo");
        }
        try {
            RolesEntity entity = convertirAEntity(jsonDTO);
            RolesEntity entitySave = repo.save(entity);
            return convertirADTO(entitySave);
        }catch (Exception e){
            throw  new RolNoEncontrado("Error al insertar el rol");
        }
    }

    /**
     *
     * @param id
     * @param jsonDTO
     * @return
     */
    public RolesDTO actualizar(Long id, @Valid RolesDTO jsonDTO){
        if (jsonDTO == null){
            throw new IllegalArgumentException("El valor del rol no puede ser nulo");
        }

        RolesEntity objEntity = repo.findById(id).orElseThrow(() -> new RolNoEncontrado("No se ha encontrado el rol"));
        objEntity.setRol(jsonDTO.getRol());
        return convertirADTO(objEntity);
    }

    /**
     *
     * @param id
     * @return
     */
    public Boolean eliminar(Long id){
        try {
            RolesEntity entity = repo.findById(id).orElse(null);
            if (entity != null){
                repo.deleteById(id);
                return true;
            }
            return false;
        }catch (EmptyResultDataAccessException e){

            throw new EmptyResultDataAccessException("Error al eliminar el rol con el id: " + id, 1);
        }
    }


    /**
     *
     * @param json
     * @return
     */

    public RolesEntity convertirAEntity(@Valid RolesDTO json){
        RolesEntity objEntity = new RolesEntity();
        objEntity.setRol(json.getRol());
        objEntity.setCodigo_rol(json.getCodigo_rol());
        return objEntity;
    }


    /**
     *
     * @param objEntity
     * @return
     */
    public RolesDTO convertirADTO(RolesEntity objEntity){
        RolesDTO dto = new RolesDTO();
        dto.setRol(objEntity.getRol());
        dto.setCodigo_rol(objEntity.getCodigo_rol());
        return dto;
    }

}
