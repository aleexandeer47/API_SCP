package API.APISCP.Services.Empresas;

import API.APISCP.Entities.Empresas.EmpresasEntity;
import API.APISCP.Exceptions.Empresas.EmpresaNoEncontrada;
import API.APISCP.Models.DTO.Empresas.EmpresasDTO;
import API.APISCP.Repositories.Empresas.EmpresasRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmpresaService {

    @Autowired
    private EmpresasRepository repo;

    /**
     * OBTENER
     * @return
     */

    public List <EmpresasDTO> obtenerEmpresas(){
        List<EmpresasEntity> listEnity = repo.findAll();
        return listEnity.stream()
                .map(this :: convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * INSERTAR
     * @param jsonDATA
     * @return
     */
    public EmpresasDTO insertar(@Valid EmpresasDTO jsonDATA ){
        if (jsonDATA == null){
            throw new IllegalArgumentException("La empresa no puede ser con valor nulo");
        }try {
            EmpresasEntity empresasEntity = convertirAEntity(jsonDATA);
            EmpresasEntity empresasSave = repo.save(empresasEntity);
            return  convertirADTO(empresasSave);

        }catch (Exception e){
            throw  new EmpresaNoEncontrada("Error al insertar la empresa " + e.getMessage());
        }
    }

    /**
     * ACTUALIZAR
     * @param id
     * @param jsonDTO
     * @return
     */
    public EmpresasDTO actualizar(Long id, @Valid EmpresasDTO jsonDTO){
        if (jsonDTO == null){
            throw  new IllegalArgumentException("El valor no puede ser nulo");
        }
        EmpresasEntity objEntity = repo.findById(id).orElseThrow(() -> new EmpresaNoEncontrada("No se ha encontrado a la empresa"));
        objEntity.setNit(jsonDTO.getNit());
        objEntity.setNombre(jsonDTO.getNombre());
        EmpresasEntity empresasSave = repo.save(objEntity);
        return convertirADTO(empresasSave);
    }

    /**
     * ELIMINAR
     * @param id
     * @return
     */

    public boolean eliminar(Long id){
        try {
            EmpresasEntity empresasEntity = repo.findById(id).orElse(null);
            if (empresasEntity != null){
                repo.deleteById(id);
                return true;
            }
            return false;
        }catch (EmptyResultDataAccessException e){
            throw  new EmptyResultDataAccessException("No se ha encontrado a la empresa con el id: " + id, 1);
        }
    }

    /**
     *
     * @param objEntity
     * @return
     */
    public EmpresasDTO convertirADTO(EmpresasEntity objEntity){
        EmpresasDTO dto = new EmpresasDTO();
        dto.setCodigo_e(objEntity.getCodigo_e());
        dto.setNit(objEntity.getNit());
        dto.setNombre(objEntity.getNombre());
        return dto;
    }

    /**
     *
     * @param json
     * @return
     */
    public EmpresasEntity convertirAEntity(@Valid EmpresasDTO json){
        EmpresasEntity entity = new EmpresasEntity();
        entity.setCodigo_e(json.getCodigo_e());
        entity.setNombre(json.getNombre());
        entity.setNit(json.getNit());
        return entity;
    }
}
