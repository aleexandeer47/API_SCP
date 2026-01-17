package API.APISCP.Services.TableIds;

import API.APISCP.Entities.TableIds.TableIdEntity;
import API.APISCP.Exceptions.TableIds.TableidNoEncontrado;
import API.APISCP.Models.DTO.TableIds.TableIdsDTO;
import API.APISCP.Repositories.TableIds.TableIdRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TableIdService {

    @Autowired
    private TableIdRepository repo;

    /**
     *
     * @return
     */

    @Transactional(readOnly = true )
    public List<TableIdsDTO>obtener(){
        List<TableIdEntity> entityList = repo.findAll();
        return entityList.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param jsonData
     * @return
     */
    @Transactional
    public TableIdsDTO insertarTableIds(@Valid TableIdsDTO jsonData){
        if (jsonData == null){
            throw new IllegalArgumentException("El valor del id de la tabla no puede ser nula");

        }
        try {
            TableIdEntity entity = convertirAEntity(jsonData);
            TableIdEntity entitySave = repo.save(entity);
            return convertirADTO(entitySave);
        }catch (Exception e){
            throw new TableidNoEncontrado("Error al insertar" + e.getMessage());
        }
    }

    /**
     *
     * @param id
     * @param jsonDTO
     * @return
     */

    @Transactional
    public TableIdsDTO actualizarTableIds(Long id, @Valid TableIdsDTO jsonDTO){
        if (jsonDTO == null){
            throw new TableidNoEncontrado("El valor de la tabla id no puede ser nulo");
        }
       TableIdEntity entity = repo.findById(id).orElseThrow(() -> new TableidNoEncontrado("No se ha encontrado la tabla id"));
        entity.setNombre_tabla(jsonDTO.getNombre_tabla());
        entity.setCorrelativo(jsonDTO.getCorrelativo());
        return convertirADTO(entity);
    }

    @Transactional
    public boolean Eliminar(Long id){
     try {
         TableIdEntity entity = repo.findById(id).orElse(null);
         if (entity != null){
             repo.deleteById(id);
             return true;
         }
         return false;
    }catch (EmptyResultDataAccessException e){
        throw  new EmptyResultDataAccessException("No se ha podido eliminar la tabla id con el id: " + id, 1 );
        }
    }


    /**
     *
     * @param json
     * @return
     */

    public TableIdEntity convertirAEntity(@Valid TableIdsDTO json){
        TableIdEntity entity = new TableIdEntity();
        entity.setCorrelativo(json.getCorrelativo());
        entity.setNombre_tabla(json.getNombre_tabla());
        entity.setCodigo_tabla(json.getCodigo_tabla());
        return entity;
    }


    /**
     *
     * @param objEntity
     * @return
     */
    public TableIdsDTO convertirADTO(TableIdEntity objEntity){
        TableIdsDTO dto = new TableIdsDTO();
        dto.setCodigo_tabla(objEntity.getCodigo_tabla());
        dto.setCorrelativo(objEntity.getCorrelativo());
        dto.setNombre_tabla(objEntity.getNombre_tabla());
        return dto;
    }


}
