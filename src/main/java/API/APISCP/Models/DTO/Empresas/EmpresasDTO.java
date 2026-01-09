package API.APISCP.Models.DTO.Empresas;

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
public class EmpresasDTO {

    @Positive(message = "El id debe de ser positivo")
    private Long codigo_e;
    @NotBlank
    private String nombre;
    @NotBlank
    private String nit;

}
