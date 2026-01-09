package API.APISCP.Models.DTO.Materiales;

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
public class MaterialesDTO {

    @Positive(message = "El id debe de ser positivo")
    private Long codigo_m;
    @NotBlank
    private String descripcion;
    @Positive(message = "El id debe de ser positivo")
    private Long codigo_cm;
    @Positive(message = "El id debe de ser positivo")
    private Long codigo_e;

}
