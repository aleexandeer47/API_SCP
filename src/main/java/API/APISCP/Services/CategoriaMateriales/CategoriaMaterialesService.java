package API.APISCP.Services.CategoriaMateriales;

import API.APISCP.Entities.CategoriaMateriales.CategoriaMaterialesEntity;
import API.APISCP.Entities.Empresas.EmpresasEntity;
import API.APISCP.Exceptions.CategoriaMaterial.ExceptionCategoriaMaterialNoFound;
import API.APISCP.Models.DTO.CategoriaMaterial.CategoriaMaterialDTO;
import API.APISCP.Repositories.CategoriaMateriales.CategoriaMaterialRepository;
import API.APISCP.Repositories.Empresas.EmpresasRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoriaMaterialesService {

    @Autowired
    private CategoriaMaterialRepository repo;

    @Autowired
    private EmpresasRepository empresasRepository;

    /**
     *Metodo de lectura sin paginacion
     * @return
     */

    public List<CategoriaMaterialDTO> obtenerCategoriasM(){

    List <CategoriaMaterialesEntity> ListEntity = repo.findAll();
        return ListEntity.stream()
                .map(this::convertiraDTO)
                .collect(Collectors.toList());

    }

    /**
     * Metodo para insertar
     * @param jsonData
     * @return
     */
    public CategoriaMaterialDTO insertar(@Valid CategoriaMaterialDTO jsonData){
        if (jsonData == null){
            throw  new IllegalArgumentException("La categoria del material no puede ser nula");
        }
        try {
            CategoriaMaterialesEntity categoriaMEntity = convertiraEntity(jsonData);
            CategoriaMaterialesEntity categoriaMaterialSave = repo.save(categoriaMEntity);
            return convertiraDTO(categoriaMaterialSave);
        }catch (Exception e){
            log.error("Error al registrar la categoria del material " + e.getMessage());
            throw  new ExceptionCategoriaMaterialNoFound("Error al registrar la categoria " + e.getMessage());
        }
    }

    /**
     * Metodo para actualizar
     * @param id
     * @param jsonDTO
     * @return
     */
    public CategoriaMaterialDTO actualizar(Long id,  @Valid  CategoriaMaterialDTO jsonDTO){
        if (jsonDTO == null){
            throw new IllegalArgumentException("La categoria del material no puede ser nula");
        }
        CategoriaMaterialesEntity categoriaMDATA = repo.findById(id).orElseThrow(() -> new ExceptionCategoriaMaterialNoFound("No se encontro la categoria del material"));
        categoriaMDATA.setDescripcion(jsonDTO.getDescripcion());
        CategoriaMaterialesEntity categoriaMUpdate = repo.save(categoriaMDATA);
        return convertiraDTO(categoriaMUpdate);
    }

    /**
     * Metodo para eliminar
     * @param id
     * @return
     */

    public boolean eliminar(Long id){
        try{
            CategoriaMaterialesEntity objEntity = repo.findById(id).orElse(null);
            if (objEntity != null){
                repo.deleteById(id);
                return true;
            }
        return false;
        }catch (EmptyResultDataAccessException e){
            throw  new EmptyResultDataAccessException("No se encontro la categoria del material con el id : " + id,1);
        }
    }





    /**
     *
     * @param objEntity
     * @return
     */

    //METODO QUE SE OCUPA PARA CONVERTIR DE ENTITY A DTO
    private CategoriaMaterialDTO convertiraDTO(CategoriaMaterialesEntity objEntity) {

        CategoriaMaterialDTO dto = new CategoriaMaterialDTO();
        dto.setCodigo_cm(objEntity.getCodigo_cm());
        dto.setDescripcion(objEntity.getDescripcion());
        dto.setCodigo_e(objEntity.getCodigo_e().getCodigo_e());
        return dto;
   }

    /**
     *
     *
     * @param json
     * @return
     */
   //EMTODO QUE SE OCUPA DE CONVERTIR DE DTO A ENTITY
   private CategoriaMaterialesEntity convertiraEntity(@Valid CategoriaMaterialDTO json){
    CategoriaMaterialesEntity objEntity = new CategoriaMaterialesEntity();
    objEntity.setCodigo_cm(json.getCodigo_cm());
    objEntity.setDescripcion(json.getDescripcion());
    EmpresasEntity empresasEntity = empresasRepository.findById(json.getCodigo_e()).orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
    objEntity.setCodigo_e(empresasEntity);
    return objEntity;
   }


}
