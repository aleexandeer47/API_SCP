package API.APISCP.Services.CostosIndirectos;

import API.APISCP.Entities.CostosIndirectos.CostosIndirectosEntity;
import API.APISCP.Entities.Empresas.EmpresasEntity;
import API.APISCP.Entities.Materiales.MaterialesEntity;
import API.APISCP.Exceptions.CostosIndirectos.CostoIndirectoNoEncontrada;
import API.APISCP.Exceptions.Empresas.EmpresaNoEncontrada;
import API.APISCP.Exceptions.Materiales.MaterialNoEncontrado;
import API.APISCP.Models.DTO.CategoriaMaterial.CategoriaMaterialDTO;
import API.APISCP.Models.DTO.CostosIndirectos.CostosIndirectosDTO;
import API.APISCP.Models.DTO.Materiales.MaterialesDTO;
import API.APISCP.Repositories.CostosIndirectos.CostosIndirectosRepository;
import API.APISCP.Repositories.Empresas.EmpresasRepository;
import API.APISCP.Repositories.Materiales.MaterialesRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CostosIndirectosService {

    @Autowired
    private CostosIndirectosRepository repo;

    @Autowired
    private EmpresasRepository repositoryE;

    @Autowired
    private MaterialesRepository repositoryM;

    /**
     *
     * @return
     */

    public List <CostosIndirectosDTO> obtenerCostosI(){
        List <CostosIndirectosEntity> ListEntity = repo.findAll();
        return ListEntity.stream()
                .map(this:: ConvertiraDTO)
                .collect(Collectors.toList());
    }


    /**
     *
     * @param jsonDATA
     * @return
     */
    public CostosIndirectosDTO insertar(@Valid CostosIndirectosDTO jsonDATA){
        if (jsonDATA == null){
            throw  new IllegalArgumentException("Los costos indirectos no pueden ser nulos");
        }
        try {
            CostosIndirectosEntity costosIndirectosEntity = ConvertiraEntity(jsonDATA);
            CostosIndirectosEntity costosIndirectosSave = repo.save(costosIndirectosEntity);
            return ConvertiraDTO(costosIndirectosSave);
        }catch (Exception e){
            log.error("Error al registrar el costos indirecto " + e.getMessage());
            throw  new CostoIndirectoNoEncontrada("Error al registrar el costo indirecto: " + e.getMessage());
        }
    }

    /**
     *
     * @param id
     * @param jsonDTO
     * @return
     */

    public CostosIndirectosDTO actualizar(Long id, @Valid CostosIndirectosDTO jsonDTO){
        if (jsonDTO == null){
            throw new IllegalArgumentException("El costo indirecto no puede ser nula");
        }
        CostosIndirectosEntity CostosIDATA = repo.findById(id).orElseThrow(() -> new CostoIndirectoNoEncontrada("No se ha encontrado el costo indirecto: "));
        CostosIDATA.setPrecio(jsonDTO.getPrecio());
        CostosIndirectosEntity costosIndirectosUpdate = repo.save(CostosIDATA);
        return ConvertiraDTO(costosIndirectosUpdate);
    }

    /**
     *
     * @param id
     * @return
     */
    public boolean eliminar (Long id){
        try {
            CostosIndirectosEntity objEntity = repo.findById(id).orElse(null);
            if (objEntity != null){
                repo.deleteById(id);
                return true;
            }
            return false;
        }catch (EmptyResultDataAccessException e){
            throw  new EmptyResultDataAccessException("No se encontro el costo indirecto con el id: " + id, 1);
        }
    }


    /**
     *
     * @param objEntity
     * @return
     */
    private CostosIndirectosDTO ConvertiraDTO(CostosIndirectosEntity objEntity){
        CostosIndirectosDTO dto = new CostosIndirectosDTO();
        dto.setCodigo_ci(objEntity.getCodigo_ci());
        dto.setPrecio(objEntity.getPrecio());
        dto.setCodigo_e(objEntity.getCodigo_e().getCodigo_e());
        dto.setCodigo_m(objEntity.getCodigo_m().getCodigo_m());
        return dto;
    }


    /**
     *
     * @param json
     * @return
     */
    private CostosIndirectosEntity ConvertiraEntity(@Valid CostosIndirectosDTO json){
        CostosIndirectosEntity objEntity = new CostosIndirectosEntity();
        objEntity.setCodigo_ci(json.getCodigo_ci());
        objEntity.setPrecio(json.getPrecio());
        EmpresasEntity empresasEntity = repositoryE.findById(json.getCodigo_e()).orElseThrow(() -> new EmpresaNoEncontrada("No se ha encontrado el id de la empresa"));
        objEntity.setCodigo_e(empresasEntity);
        MaterialesEntity materialesEntity = repositoryM.findById(json.getCodigo_m()).orElseThrow(() -> new MaterialNoEncontrado("No se ha encontrado el id del material"));
        objEntity.setCodigo_m(materialesEntity);
        return objEntity;
    }



}
