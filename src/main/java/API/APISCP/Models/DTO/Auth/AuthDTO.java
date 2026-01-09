package API.APISCP.Models.DTO.Auth;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AuthDTO {

    @NotBlank(message = "Para iniciar sesion es requerido")
    private String usuario;
    @NotBlank(message = "Para iniciar sesión la contraseña es requerida")
    private String contrasenia;
    private String rol;
}
