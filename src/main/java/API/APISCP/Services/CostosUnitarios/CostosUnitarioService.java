package API.APISCP.Services.CostosUnitarios;
import API.APISCP.Entities.CostosIndirectos.CostosIndirectosEntity;
import API.APISCP.Entities.CostosUnitarios.CostosUnitariosEntity;
import API.APISCP.Entities.Empresas.EmpresasEntity;
import API.APISCP.Entities.Materiales.MaterialesEntity;
import API.APISCP.Entities.Partidas.PartidasEntity;
import API.APISCP.Entities.PrecioUnitario.PrecioUnitarioEntity;
import API.APISCP.Exceptions.CostosIndirectos.CostoIndirectoNoEncontrada;
import API.APISCP.Exceptions.CostosUnitarios.CostoUnitarioNoEncontrado;
import API.APISCP.Exceptions.Empresas.EmpresaNoEncontrada;
import API.APISCP.Exceptions.Partidas.PartidaNoEncontrada;
import API.APISCP.Exceptions.PrecioUnitario.PrecioUnitarioNoEncontrado;
import API.APISCP.Models.DTO.CostosUnitarios.CostosUnitariosDTO;
import API.APISCP.Repositories.CostosIndirectos.CostosIndirectosRepository;
import API.APISCP.Repositories.CostosUnitarios.CostosUnitariosRepository;
import API.APISCP.Repositories.Empresas.EmpresasRepository;
import API.APISCP.Repositories.Materiales.MaterialesRepository;
import API.APISCP.Repositories.Partidas.PartidasRepository;
import API.APISCP.Repositories.PrecioUnitario.PrecioUnitarioRepository;
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
public class CostosUnitarioService {

    @Autowired
    private CostosUnitariosRepository repo;

    @Autowired
    private MaterialesRepository repositoryM;

    @Autowired
    private PartidasRepository repositoryP;

    @Autowired
    private CostosIndirectosRepository repositoryCI;

    @Autowired
    private EmpresasRepository repositoryE;

    @Autowired
    private PrecioUnitarioRepository repositoryPU;

    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List <CostosUnitariosDTO> obtenerCostosU(){
        List<CostosUnitariosEntity> ListEntity = repo.findAll();
        return ListEntity.stream()
                .map(this:: convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param jsonDATA
     * @return
     */

    @Transactional
    public CostosUnitariosDTO insertar(@Valid CostosUnitariosDTO jsonDATA){
        if (jsonDATA == null){
            throw  new IllegalArgumentException("Los costos unitarios no pueden ser nulos");
        }
        try {
            CostosUnitariosEntity costosUnitariosEntity = convertirAEntity(jsonDATA);
            CostosUnitariosEntity costosUnitarioSave = repo.save(costosUnitariosEntity);
            return  convertirADTO(costosUnitarioSave);
        }catch (Exception e){
            throw  new CostoUnitarioNoEncontrado("Error al registrar el costo unitario " + e.getMessage());
        }
    }

    /**
     *
     * @param id
     * @param jsonDTO
     * @return
     */
    @Transactional
    public CostosUnitariosDTO actualizar(Long id, @Valid CostosUnitariosDTO jsonDTO){
        if (jsonDTO == null){
            throw  new IllegalArgumentException("El costo unitario no puede ser nulo");
        }

        CostosUnitariosEntity costosUEntity = repo.findById(id).orElseThrow(() -> new CostoUnitarioNoEncontrado("No se a encontrado el costo unitario"));
        costosUEntity.setCantidad_material(jsonDTO.getCantidad_material());
        costosUEntity.setRendimiento(jsonDTO.getRendimiento());
        costosUEntity.setCalculo_precioU(jsonDTO.getCalculo_precioU());
        costosUEntity.setTotal(jsonDTO.getTotal());
        CostosUnitariosEntity costosUnitariosSave = repo.save(costosUEntity);
        return convertirADTO(costosUnitariosSave);
    }

    /**
     *
     * @param id
     * @return
     */
    @Transactional
     public boolean eliminar(Long id){
        try {
            CostosUnitariosEntity objEntity = repo.findById(id).orElse(null);
            if (objEntity != null){
                repo.deleteById(id);
                return true;
            }
            return false;
        }catch (EmptyResultDataAccessException e){
            throw  new EmptyResultDataAccessException("No se ha encontrado el costo unitario con el id: " + id, 1);
        }
    }


    /**
     *
     * @param objEntity
     * @return
     */
    public CostosUnitariosDTO convertirADTO(CostosUnitariosEntity objEntity){
        CostosUnitariosDTO dto = new CostosUnitariosDTO();
        dto.setCodigo_cu(objEntity.getCodigo_cu());
        dto.setCantidad_material(objEntity.getCantidad_material());
        dto.setRendimiento(objEntity.getRendimiento());
        dto.setCalculo_precioU(objEntity.getCalculo_precioU());
        dto.setTotal(objEntity.getTotal());
        dto.setCodigo_e(objEntity.getCodigo_m().getCodigo_m());
        dto.setCodigo_p(objEntity.getCodigo_p().getCodigo_p());
        dto.setCodigo_ci(objEntity.getCodigo_ci().getCodigo_ci());
        dto.setCodigo_pu(objEntity.getCodigo_pu().getCodigo_pu());
        return dto;
    }

    /**
     *
     * @param json
     * @return
     */
    public CostosUnitariosEntity convertirAEntity(@Valid CostosUnitariosDTO json){
        CostosUnitariosEntity objEntity = new CostosUnitariosEntity();
        objEntity.setCodigo_cu(json.getCodigo_cu());
        objEntity.setCantidad_material(json.getCantidad_material());
        objEntity.setRendimiento(json.getRendimiento());
        objEntity.setCalculo_precioU(json.getCalculo_precioU());
        objEntity.setTotal(json.getTotal());
        EmpresasEntity EmpresaEntity = repositoryE.findById(json.getCodigo_e()).orElseThrow(() -> new EmpresaNoEncontrada("No se ha encontrado la empresa"));
        objEntity.setCodigo_e(EmpresaEntity);
        PartidasEntity PartidasEntity = repositoryP.findById(json.getCodigo_p()).orElseThrow(() -> new PartidaNoEncontrada("No se ha encontrado la partida"));
        objEntity.setCodigo_p(PartidasEntity);
        CostosIndirectosEntity CostosCIEntity = repositoryCI.findById(json.getCodigo_ci()).orElseThrow(() -> new CostoIndirectoNoEncontrada("No se ha encontrado el Costo indirecto"));
        objEntity.setCodigo_ci(CostosCIEntity);
        PrecioUnitarioEntity PrecioUnitarioEntity =repositoryPU.findById(json.getCodigo_pu()).orElseThrow(() -> new PrecioUnitarioNoEncontrado("No se ha encontrado el precio Unitario"));
        objEntity.setCodigo_pu(PrecioUnitarioEntity);
        return objEntity;
    }


}
