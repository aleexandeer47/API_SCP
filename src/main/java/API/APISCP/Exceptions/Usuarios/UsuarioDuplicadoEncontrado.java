package API.APISCP.Exceptions.Usuarios;

public class UsuarioDuplicadoEncontrado extends RuntimeException {
    public UsuarioDuplicadoEncontrado(String message) {
        super(message);
    }
}
