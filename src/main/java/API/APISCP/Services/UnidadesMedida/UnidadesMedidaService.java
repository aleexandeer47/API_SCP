package API.APISCP.Services.UnidadesMedida;


import API.APISCP.Entities.Empresas.EmpresasEntity;
import API.APISCP.Entities.UnidadesMedida.UnidadesMedidaEntity;
import API.APISCP.Exceptions.Empresas.EmpresaNoEncontrada;
import API.APISCP.Exceptions.UnidadesMedida.UnidadMedidaNoEncontrado;
import API.APISCP.Models.DTO.UnidadesMedida.UnidadesMedidaDTO;
import API.APISCP.Repositories.Empresas.EmpresasRepository;
import API.APISCP.Repositories.UnidadesMedida.UnidadesMedidaRepository;
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
public class UnidadesMedidaService {

    @Autowired
    private UnidadesMedidaRepository repo;

    @Autowired
    private EmpresasRepository empresasRepository;


    /**
     * Obtener
     * @return
     */

    @Transactional(readOnly = true)
    public List<UnidadesMedidaDTO>obtenerUnidadesM(){
        List<UnidadesMedidaEntity> ListEntity = repo.findAll();
        return ListEntity.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param jsonDATA
     * @return
     */
    @Transactional
    public UnidadesMedidaDTO insertar(@Valid UnidadesMedidaDTO jsonDATA){
        if (jsonDATA == null){
            throw  new IllegalArgumentException("La unidad de medida no puede ser nula");
        }
        try {
            UnidadesMedidaEntity entity = convertirAEntity(jsonDATA);
            UnidadesMedidaEntity entitySave = repo.save(entity);
            return convertirADTO(entitySave);
        }catch (Exception e){
            throw  new UnidadMedidaNoEncontrado("Error al insertar los datos" + e.getMessage());
        }
    }

    /**
     *
     * @param id
     * @param jsonDTO
     * @return
     */
    @Transactional
    public UnidadesMedidaDTO actualizar(Long id , @Valid UnidadesMedidaDTO jsonDTO){
        if (jsonDTO == null){
            throw  new UnidadMedidaNoEncontrado("El valor de la unidad de medida no puede ser nulo");
        }
        UnidadesMedidaEntity objEntity = repo.findById(id).orElseThrow(() -> new UnidadMedidaNoEncontrado("No se ha encontrado la unidad de medida"));
        objEntity.setAbreviatura(jsonDTO.getAbreviatura());
        objEntity.setNombre(jsonDTO.getNombre());
        UnidadesMedidaEntity unidadesMedidaEntity = repo.save(objEntity);
        return convertirADTO(unidadesMedidaEntity);

    }

    /**
     *
     * @param id
     * @return
     */

    @Transactional
    public Boolean eliminar(Long id){
        try {
            UnidadesMedidaEntity entity = repo.findById(id).orElse(null);
            if (entity != null){
                repo.deleteById(id);
                return true;
            }
            return false;
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se ha encontrado la unidad de medida con el id: " + id, 1);
        }

    }


    /**
     *
     * @param objEntity
     * @return
     */
    public UnidadesMedidaDTO convertirADTO(UnidadesMedidaEntity objEntity){
        UnidadesMedidaDTO dto = new UnidadesMedidaDTO();
        dto.setCodigo_u(objEntity.getCodigo_u());
        dto.setAbreviatura(objEntity.getAbreviatura());
        dto.setNombre(objEntity.getNombre());
        dto.setCodigo_e(objEntity.getCodigo_e().getCodigo_e());
        return dto;
    }

    /**
     *
     * @param json
     * @return
     */
    public UnidadesMedidaEntity convertirAEntity(@Valid UnidadesMedidaDTO json){
        UnidadesMedidaEntity entity = new UnidadesMedidaEntity();
        entity.setCodigo_u(json.getCodigo_u());
        entity.setNombre(json.getNombre());
        entity.setAbreviatura(json.getAbreviatura());
        EmpresasEntity empresasEntity = empresasRepository.findById(json.getCodigo_e()).orElseThrow(() -> new EmpresaNoEncontrada("No se ha encontrado la empresa"));
        entity.setCodigo_e(empresasEntity);
        return entity;
    }
}
