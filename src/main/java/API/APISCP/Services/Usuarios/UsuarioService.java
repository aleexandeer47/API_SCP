package API.APISCP.Services.Usuarios;


import API.APISCP.Entities.Empresas.EmpresasEntity;
import API.APISCP.Entities.Roles.RolesEntity;
import API.APISCP.Entities.Usuarios.UsuariosEntity;
import API.APISCP.Exceptions.Empresas.EmpresaNoEncontrada;
import API.APISCP.Exceptions.Roles.RolNoEncontrado;
import API.APISCP.Exceptions.Usuarios.UsuarioNoEncontrado;
import API.APISCP.Models.DTO.Usuarios.UsuariosDTO;
import API.APISCP.Repositories.Empresas.EmpresasRepository;
import API.APISCP.Repositories.Roles.RolesRepository;
import API.APISCP.Repositories.Usuarios.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UsuarioService {

    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private EmpresasRepository empresasRepository;

    @Autowired
    private RolesRepository rolesRepository;

    public List<UsuariosDTO>obtenerUsuarios(){
        List<UsuariosEntity> listEntity = repo.findAll();
        return listEntity.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param jsonDATA
     * @return
     */
    public UsuariosDTO insertar(@Valid UsuariosDTO jsonDATA){
        if (jsonDATA == null){
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }

        try {
            UsuariosEntity usuariosEntity = convertirAEntity(jsonDATA);
            UsuariosEntity usuariosSave = repo.save(usuariosEntity);
            return convertirADTO(usuariosSave);
        }catch (Exception e){
            throw  new UsuarioNoEncontrado("Error al insertar el usuario  " + e.getMessage());
        }
    }


    /**
     *
     * @param objEntity
     * @return
     */
    public UsuariosDTO convertirADTO(UsuariosEntity objEntity){
        UsuariosDTO dto = new UsuariosDTO();
        dto.setCodigo_usuario(objEntity.getCodigo_usuario());
        dto.setUsuario(objEntity.getUsuario());
        dto.setContrasena(objEntity.getContrasena());
        dto.setCodigo_rol(objEntity.getCodigo_rol().getCodigo_rol());
        dto.setCodigo_e(objEntity.getCodigo_e().getCodigo_e());
        return dto;
    }


    /**
     *
     * @param id
     * @param jsonDTO
     * @return
     */
    public UsuariosDTO actualizar(Long id, @Valid UsuariosDTO jsonDTO){
        if (jsonDTO == null){
            throw  new UsuarioNoEncontrado("El usuario no puede ser nulo");
        }
        UsuariosEntity usuariosEntity = repo.findById(id).orElseThrow(() -> new UsuarioNoEncontrado("No se ha encontrado al usuario"));
        usuariosEntity.setUsuario(jsonDTO.getUsuario());
        usuariosEntity.setContrasena(jsonDTO.getContrasena());
        return convertirADTO(usuariosEntity);
    }

    /**
     * 
     * @param id
     * @return
     */

    public boolean eliminar(Long id){
        try {
            UsuariosEntity usuariosEntity = repo.findById(id).orElse(null);
            if (usuariosEntity != null){
                 repo.deleteById(id);
                 return true;
            }
            return false;
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("Error al eliminar el usuario con el id: " + id, 1 );

        }
    }

    /**
     *
     * @param jsonDTO
     * @return
     */
    public UsuariosEntity convertirAEntity(@Valid UsuariosDTO jsonDTO){
        UsuariosEntity entity = new  UsuariosEntity();
        entity.setCodigo_usuario(jsonDTO.getCodigo_usuario());
        entity.setUsuario(jsonDTO.getUsuario());
        entity.setContrasena(jsonDTO.getContrasena());
        RolesEntity rolesEntity = rolesRepository.findById(jsonDTO.getCodigo_rol()).orElseThrow(() -> new RolNoEncontrado("No se ha encontrado el rol"));
        entity.setCodigo_rol(rolesEntity);
        EmpresasEntity empresasEntity = empresasRepository.findById(jsonDTO.getCodigo_e()).orElseThrow(()-> new EmpresaNoEncontrada("No se ha encontrado a la empresa"));
        entity.setCodigo_e(empresasEntity);
        return entity;
    }
}
