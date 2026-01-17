package API.APISCP.Services.Auth;

import API.APISCP.Config.Cripto.Argon2Password;
import API.APISCP.Entities.Usuarios.UsuariosEntity;
import API.APISCP.Repositories.Auth.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthRepository repo;

    @Autowired
    private Argon2Password argon2;

    /**
     * Login SOLO LECTURA
     * Necesita transacción por:
     * - Relaciones LAZY (codigo_rol)
     * - Consistencia de sesión
     */
    @Transactional(readOnly = true)
    public boolean login(String usuario, String contrasena) {

        Optional<UsuariosEntity> userOpt = repo.findByUsuario(usuario);

        if (userOpt.isEmpty()) {
            return false;
        }

        UsuariosEntity user = userOpt.get();


        System.out.println(
                "Usuario encontrado ID: " + user.getCodigo_usuario() +
                        ", usuario: " + user.getUsuario() +
                        ", rol: " + user.getCodigo_rol().getRol()
        );

        return argon2.verificarContrasenia(
                user.getContrasena(),
                contrasena
        );
    }

    /**
     * Obtener usuario completo
     * También SOLO LECTURA
     */
    @Transactional(readOnly = true)
    public Optional<UsuariosEntity> obtenerUsuario(String usuario) {
        return repo.findByUsuario(usuario);
    }
}
