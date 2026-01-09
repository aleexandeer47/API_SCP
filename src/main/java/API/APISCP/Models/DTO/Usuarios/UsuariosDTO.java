package API.APISCP.Models.DTO.Usuarios;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UsuariosDTO {

    @Positive(message = "El id debe de ser positivo")
    private Long codigo_usuario;

    private String usuario;
    @Size(min = 8, message = "La contrasena debe ser un minimo de 8 caracteres")
    private String contrasena;
    @Positive(message = "El id debe de ser positivo")
    private Long codigo_rol;
    @Positive(message = "El id debe de ser positivo")
    private Long codigo_e;
}
