package API.APISCP.Services.Materiales;

import API.APISCP.Entities.CategoriaMateriales.CategoriaMaterialesEntity;
import API.APISCP.Entities.CostosUnitarios.CostosUnitariosEntity;
import API.APISCP.Entities.Empresas.EmpresasEntity;
import API.APISCP.Entities.Materiales.MaterialesEntity;
import API.APISCP.Exceptions.CategoriaMaterial.ExceptionCategoriaMaterialNoFound;
import API.APISCP.Exceptions.Empresas.EmpresaNoEncontrada;
import API.APISCP.Exceptions.Materiales.MaterialNoEncontrado;
import API.APISCP.Models.DTO.Materiales.MaterialesDTO;
import API.APISCP.Repositories.CategoriaMateriales.CategoriaMaterialRepository;
import API.APISCP.Repositories.CostosUnitarios.CostosUnitariosRepository;
import API.APISCP.Repositories.Empresas.EmpresasRepository;
import API.APISCP.Repositories.Materiales.MaterialesRepository;
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
public class MaterialesService {

    @Autowired
    private MaterialesRepository repo;

    @Autowired
    private CategoriaMaterialRepository repositoryCategoriaM;

    @Autowired
    private EmpresasRepository repositoryEmpresa;


    /**
     * Obtener
     * @return
     */

    @Transactional(readOnly = true)
    public List<MaterialesDTO> obtenerMateriales(){
        List<MaterialesEntity> ListMaterialesEntity = repo.findAll();
        return  ListMaterialesEntity.stream()
                .map(this :: convertirADTO )
                .collect(Collectors.toList());
    }

    /**
     * INSERTAR
     * @param JsonData
     * @return
     */
    @Transactional
    public MaterialesDTO insertar(@Valid MaterialesDTO JsonData){
        if (JsonData == null){
            throw  new IllegalArgumentException("Los materiales no pueden ser nulos");
        }
        try {
            MaterialesEntity objEntity = convertirAEntity(JsonData);
            MaterialesEntity materialesSave = repo.save(objEntity);
            return convertirADTO(materialesSave);

        }catch (Exception e){
            throw new MaterialNoEncontrado("Error al registrar el material");
        }
    }

    /**
     * ACTUALIZAR
     * @param id
     * @param jsonDTO
     * @return
     */

    @Transactional
    public MaterialesDTO actualizar(Long id, @Valid MaterialesDTO jsonDTO){
        if (jsonDTO == null){
            throw new IllegalArgumentException("El material no puede ser nulo");
        }
        MaterialesEntity materialesEntity = repo.findById(id).orElseThrow(() -> new MaterialNoEncontrado("No se ha encontrado el material"));
        materialesEntity.setDescripcion(jsonDTO.getDescripcion());
        MaterialesEntity materialeSave = repo.save(materialesEntity);
        return convertirADTO(materialeSave);
    }

    /**
     * ELIMINAR
     * @param id
     * @return
     */
    @Transactional
    public boolean eliminar(Long id){
        try {
            MaterialesEntity entity = repo.findById(id).orElse(null);
            if (entity != null){
                repo.deleteById(id);
                return true;
            }
            return false;
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se ha encontrado el material con el id: " + id, 1);

        }
    }

    /**
     *
     * @param objEntity
     * @return
     */
    public MaterialesDTO convertirADTO(MaterialesEntity objEntity){
        MaterialesDTO dto = new MaterialesDTO();
        dto.setCodigo_m(objEntity.getCodigo_m());
        dto.setDescripcion(objEntity.getDescripcion());
        dto.setCodigo_cm(objEntity.getCodigo_cm().getCodigo_cm());
        dto.setCodigo_e(objEntity.getCodigo_e().getCodigo_e());
        return dto;
    }

    /**
     *
     * @param json
     * @return
     */
    public MaterialesEntity convertirAEntity(@Valid MaterialesDTO json){
        MaterialesEntity entity = new MaterialesEntity();
        entity.setCodigo_m(json.getCodigo_m());
        EmpresasEntity empresasEntity = repositoryEmpresa.findById(json.getCodigo_e()).orElseThrow(() -> new EmpresaNoEncontrada("No se ha encontrado a la empresa"));
        entity.setCodigo_e(empresasEntity);
        CategoriaMaterialesEntity categoriaMaterialesEntity = repositoryCategoriaM.findById(json.getCodigo_cm()).orElseThrow(() -> new ExceptionCategoriaMaterialNoFound("No se ha encontrado la categoria del material"));
        entity.setCodigo_cm(categoriaMaterialesEntity);
        entity.setDescripcion(json.getDescripcion());
        return  entity;
    }
}
