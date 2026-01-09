package API.APISCP.Models.DTO.UnidadesMedida;

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
public class UnidadesMedidaDTO {

    @Positive(message = "El id debe de ser positivo")
    private Long codigo_u;
    @NotBlank
    private String nombre;
    @NotBlank
    private String abreviatura;
    @Positive(message = "El id debe ser positivo")
    private Long codigo_e;

}
