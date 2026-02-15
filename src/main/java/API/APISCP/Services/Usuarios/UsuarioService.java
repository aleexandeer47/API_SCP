package API.APISCP.Services.Usuarios;

import API.APISCP.Config.Cripto.Argon2Password;
import API.APISCP.Entities.Empresas.EmpresasEntity;
import API.APISCP.Entities.Roles.RolesEntity;
import API.APISCP.Entities.Usuarios.UsuariosEntity;
import API.APISCP.Exceptions.Empresas.EmpresaNoEncontrada;
import API.APISCP.Exceptions.Roles.RolNoEncontrado;
import API.APISCP.Exceptions.Usuarios.UsuarioDuplicadoEncontrado;
import API.APISCP.Exceptions.Usuarios.UsuarioNoEncontrado;
import API.APISCP.Models.DTO.Usuarios.UsuariosDTO;
import API.APISCP.Repositories.Empresas.EmpresasRepository;
import API.APISCP.Repositories.Roles.RolesRepository;
import API.APISCP.Repositories.Usuarios.UsuarioRepository;
import API.APISCP.Utils.PasswordGenerator;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private Argon2Password argon2;

    // ============================
    // OBTENER USUARIOS
    // ============================
    @Transactional(readOnly = true)
    public List<UsuariosDTO> obtenerUsuarios() {
        return repo.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // ============================
    // INSERTAR USUARIO
    // ============================
    @Transactional
    public UsuariosDTO insertar(@Valid UsuariosDTO jsonDATA) {

        if (repo.existsByUsuario(jsonDATA.getUsuario())) {
            throw new UsuarioDuplicadoEncontrado("El usuario ya existe");
        }

        UsuariosEntity entity = convertirAEntity(jsonDATA);
        entity.setContrasena(argon2.EncriptarContrasenia(jsonDATA.getContrasena()));

        return convertirADTO(repo.save(entity));
    }

    // ============================
    // ACTUALIZAR USUARIO
    // ============================
    @Transactional
    public UsuariosDTO actualizar(@Valid Long id, UsuariosDTO jsonDTO) {

        UsuariosEntity entity = repo.findById(id)
                .orElseThrow(() -> new UsuarioNoEncontrado("Usuario no encontrado"));

        if (!entity.getUsuario().equals(jsonDTO.getUsuario())
                && repo.existsByUsuario(jsonDTO.getUsuario())) {
            throw new UsuarioDuplicadoEncontrado("El usuario ya existe");
        }

        entity.setUsuario(jsonDTO.getUsuario());
        entity.setContrasena(argon2.EncriptarContrasenia(jsonDTO.getContrasena()));

        return convertirADTO(repo.save(entity));
    }

    // ============================
    // ELIMINAR USUARIO
    // ============================
    @Transactional
    public boolean eliminar(Long id) {
        UsuariosEntity existente = repo.findById(id).orElse(null);
        if (existente!=null){
            repo.deleteById(id);
            return true;
        }else {
            log.error("Usuario no encontrado");
            return false;
        }
    }

    // ============================
    // CAMBIAR CONTRASEÑA (AUTOGENERADA)
    // ============================
    @Transactional
    public boolean cambiarContrasena(@Valid Long id) {
        UsuariosEntity existente = repo.findById(id).orElseThrow(() -> new UsuarioNoEncontrado("Usuario no encontrado"));
        if (existente != null){
            String newPassword = PasswordGenerator.generateSecurePassword(12);
            existente.setContrasena(argon2.EncriptarContrasenia(newPassword));
            UsuariosEntity usuarioActualizado = repo.save(existente);
            return true;
        }
        return false;
    }

    // ============================
    // CONVERSIÓN A DTO
    // ============================
    public UsuariosDTO convertirADTO(UsuariosEntity objEntity) {

        UsuariosDTO dto = new UsuariosDTO();
        dto.setCodigo_usuario(objEntity.getCodigo_usuario());
        dto.setUsuario(objEntity.getUsuario());
        dto.setCodigo_rol(objEntity.getCodigo_rol().getCodigo_rol());
        dto.setCodigo_e(objEntity.getCodigo_e().getCodigo_e());

        return dto;
    }

    // ============================
    // CONVERSIÓN A ENTITY
    // ============================
    private UsuariosEntity convertirAEntity(@Valid UsuariosDTO jsonDTO) {

        UsuariosEntity entity = new UsuariosEntity();
        entity.setCodigo_usuario(jsonDTO.getCodigo_usuario());
        entity.setUsuario(jsonDTO.getUsuario());

        RolesEntity rol = rolesRepository.findById(jsonDTO.getCodigo_rol())
                .orElseThrow(() -> new RolNoEncontrado("Rol no encontrado"));

        EmpresasEntity empresa = empresasRepository.findById(jsonDTO.getCodigo_e())
                .orElseThrow(() -> new EmpresaNoEncontrada("Empresa no encontrada"));

        entity.setCodigo_rol(rol);
        entity.setCodigo_e(empresa);

        return entity;
    }
}
