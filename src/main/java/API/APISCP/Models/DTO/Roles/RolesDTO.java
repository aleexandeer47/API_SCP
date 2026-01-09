package API.APISCP.Models.DTO.Roles;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RolesDTO {

    @Positive(message = "El id debe de ser positivo")
    private Long codigo_rol;
    @NotBlank
    private String rol;

}
