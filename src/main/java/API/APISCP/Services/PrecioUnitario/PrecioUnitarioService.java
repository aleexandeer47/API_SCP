package API.APISCP.Services.PrecioUnitario;

import API.APISCP.Entities.Empresas.EmpresasEntity;
import API.APISCP.Entities.Materiales.MaterialesEntity;
import API.APISCP.Entities.PrecioUnitario.PrecioUnitarioEntity;
import API.APISCP.Entities.UnidadesMedida.UnidadesMedidaEntity;
import API.APISCP.Exceptions.Empresas.EmpresaNoEncontrada;
import API.APISCP.Exceptions.Materiales.MaterialNoEncontrado;
import API.APISCP.Exceptions.PrecioUnitario.PrecioUnitarioNoEncontrado;
import API.APISCP.Exceptions.UnidadesMedida.UnidadMedidaNoEncontrado;
import API.APISCP.Models.DTO.PrecioUnitario.PrecioUnitarioDTO;
import API.APISCP.Repositories.Empresas.EmpresasRepository;
import API.APISCP.Repositories.Materiales.MaterialesRepository;
import API.APISCP.Repositories.PrecioUnitario.PrecioUnitarioRepository;
import API.APISCP.Repositories.UnidadesMedida.UnidadesMedidaRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PrecioUnitarioService {

    @Autowired
    private PrecioUnitarioRepository repo;

    @Autowired
    private MaterialesRepository repositoryM;

    @Autowired
    private UnidadesMedidaRepository repositoryUM;

    @Autowired
    private EmpresasRepository repositoryE;

    /**
     *
     * @return
     */

    public List<PrecioUnitarioDTO> obtenerPrecioUnitario(){
        List<PrecioUnitarioEntity> ListEntity = repo.findAll();
                return ListEntity.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param jsonData
     * @return
     */

    public PrecioUnitarioDTO insertar(@Valid PrecioUnitarioDTO jsonData){
       if (jsonData == null){
           throw  new IllegalArgumentException("El precio unitario no puede ser nulo");
       }
       try {
           PrecioUnitarioEntity objEntity = convertirAEntity(jsonData);
           PrecioUnitarioEntity precioUnitarioSave = repo.save(objEntity);
           return convertirADTO(precioUnitarioSave);
       }catch (Exception e){
           throw  new PrecioUnitarioNoEncontrado("Error al insertar el precio unitario" + e.getMessage());
       }
    }

    /**
     *
     * @param id
     * @param jsonDTO
     * @return
     */
    public PrecioUnitarioDTO actualizar(Long id, @Valid PrecioUnitarioDTO jsonDTO){
        if (jsonDTO == null){
            throw  new IllegalArgumentException("El precio unitario no puede ser nulo");
        }

            PrecioUnitarioEntity objEntity = repo.findById(id).orElseThrow(() -> new PrecioUnitarioNoEncontrado("No se ha encontrado el precio unitario"));
            objEntity.setPresentacion(jsonDTO.getPresentacion());
            objEntity.setPrecio_unitario(jsonDTO.getPrecio_unitario());
            objEntity.setPrecio(jsonDTO.getPrecio());
            PrecioUnitarioEntity precioUnitarioSave = repo.save(objEntity);
            return convertirADTO(precioUnitarioSave);

    }

    /**
     *
     * @param id
     * @return
     */
    public boolean eliminar(Long id){
      try {

          PrecioUnitarioEntity entity = repo.findById(id).orElse(null);
          if (entity != null){
              repo.deleteById(id);
              return true;
          }
          return  false;
      }catch (EmptyResultDataAccessException e){
          throw  new EmptyResultDataAccessException("Error al eliminar el precio unitario con el id: " + id , 1);
      }
    }


    public PrecioUnitarioDTO convertirADTO(PrecioUnitarioEntity objEntity){
        PrecioUnitarioDTO dto = new PrecioUnitarioDTO();
        dto.setCodigo_pu(objEntity.getCodigo_pu());
        dto.setPrecio(objEntity.getPrecio());
        dto.setPrecio_unitario(objEntity.getPrecio_unitario());
        dto.setPresentacion(objEntity.getPresentacion());
        dto.setCodigo_m(objEntity.getCodigo_m().getCodigo_m());
        dto.setCodigo_u(objEntity.getCodigo_u().getCodigo_u());
        dto.setCodigo_e(objEntity.getCodigo_e().getCodigo_e());
        return dto;

    }

    public PrecioUnitarioEntity convertirAEntity(@Valid PrecioUnitarioDTO json){
        PrecioUnitarioEntity objEntity = new PrecioUnitarioEntity();
        objEntity.setCodigo_pu(json.getCodigo_pu());
        objEntity.setPrecio(json.getPrecio());
        objEntity.setPresentacion(json.getPresentacion());
        objEntity.setPrecio_unitario(json.getPrecio_unitario());
        MaterialesEntity entity = repositoryM.findById(json.getCodigo_m()).orElseThrow(() -> new MaterialNoEncontrado("No se ha encontrado el material"));
        objEntity.setCodigo_m(entity);
        UnidadesMedidaEntity unidadesMedidaEntity = repositoryUM.findById(json.getCodigo_u()).orElseThrow(() -> new UnidadMedidaNoEncontrado("No se ha encontrado la unidad de medida"));
        objEntity.setCodigo_u(unidadesMedidaEntity);
        EmpresasEntity empresasEntity = repositoryE.findById(json.getCodigo_e()).orElseThrow(() -> new EmpresaNoEncontrada("No se ha encontrado la empresa"));
        objEntity.setCodigo_e(empresasEntity);
        return objEntity;
    }

}
