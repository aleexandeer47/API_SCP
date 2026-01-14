package API.APISCP.Services.Partidas;

import API.APISCP.Entities.Empresas.EmpresasEntity;
import API.APISCP.Entities.Partidas.PartidasEntity;
import API.APISCP.Exceptions.Empresas.EmpresaNoEncontrada;
import API.APISCP.Exceptions.Partidas.PartidaNoEncontrada;
import API.APISCP.Models.DTO.Partidas.PartidasDTO;
import API.APISCP.Repositories.Empresas.EmpresasRepository;
import API.APISCP.Repositories.Partidas.PartidasRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PartidaService {

    @Autowired
    private PartidasRepository repo;


    @Autowired
    private EmpresasRepository repositoryE;


    /**
     *Leer
     * @return
     */
    public List<PartidasDTO> obtenerPartidas(){
        List<PartidasEntity> ListEntity = repo.findAll();
        return ListEntity.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     * insertar
     * @param jsonDATA
     * @return
     */

    public PartidasDTO insertar(@Valid PartidasDTO jsonDATA){
        if (jsonDATA == null){
            throw new IllegalArgumentException("El valor de la partida no puede ser nula");
        }
        try {
            PartidasEntity entity =  convertirEntity(jsonDATA);
            PartidasEntity entitySave = repo.save(entity);
            return  convertirADTO(entitySave);
        }catch (Exception e){
            throw  new PartidaNoEncontrada("Error al insertar la partida");
        }
    }

    /**
     * actualizar
     * @param id
     * @param jsonDTO
     * @return
     */
    public PartidasDTO actualizar(Long id, @Valid PartidasDTO jsonDTO){
        if (jsonDTO == null){
            throw  new IllegalArgumentException("La partida no puede ser nula");
        }
        PartidasEntity objEntity = repo.findById(id).orElseThrow(() -> new PartidaNoEncontrada("No se ha encontrado la partida"));
        objEntity.setProduccion_xdia(jsonDTO.getProduccion_xdia());
        objEntity.setDescripcion(jsonDTO.getDescripcion());
        objEntity.setUnidad_medida(jsonDTO.getUnidad_medida());
        objEntity.setCantidad_analizada(jsonDTO.getCantidad_analizada());
        PartidasEntity partidasEntitySave = repo.save(objEntity);
        return convertirADTO(partidasEntitySave);
    }

    /**
     * Eliminar
     * @param id
     * @return
     */
    public boolean eliminar(Long id){
     try {
         PartidasEntity objEntity = repo.findById(id).orElse(null);
         if (objEntity !=null){
             repo.deleteById(id);
             return  true;
         }
         return false;
     }catch (EmptyResultDataAccessException e){
         throw  new EmptyResultDataAccessException("Error al eliminar la partida con el id: " + id, 1);
      }
    }

    /**
     *
     * @param objEntity
     * @return
     */
    public PartidasDTO convertirADTO(PartidasEntity objEntity){
        PartidasDTO dto = new PartidasDTO();
        dto.setCodigo_p(objEntity.getCodigo_p());
        dto.setDescripcion(objEntity.getDescripcion());
        dto.setUnidad_medida(objEntity.getUnidad_medida());
        dto.setCantidad_analizada(objEntity.getCantidad_analizada());
        dto.setProduccion_xdia(objEntity.getProduccion_xdia());
        dto.setCodigo_e(objEntity.getCodigo_e().getCodigo_e());
        return dto;
    }

    /**
     *
     * @param json
     * @return
     */
    public PartidasEntity convertirEntity(@Valid PartidasDTO json){
        PartidasEntity entity = new  PartidasEntity();
        entity.setCodigo_p(json.getCodigo_p());
        entity.setDescripcion(json.getDescripcion());
        entity.setUnidad_medida(json.getUnidad_medida());
        entity.setCantidad_analizada(json.getCantidad_analizada());
        entity.setProduccion_xdia(json.getProduccion_xdia());
        EmpresasEntity EmpresaEntity = repositoryE.findById(json.getCodigo_e()).orElseThrow(() -> new EmpresaNoEncontrada("No se ha encontrado la empresa"));
        entity.setCodigo_e(EmpresaEntity);
        return  entity;
    }

}
