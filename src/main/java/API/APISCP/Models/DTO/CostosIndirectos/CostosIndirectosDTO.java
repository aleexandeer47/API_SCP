package API.APISCP.Models.DTO.CostosIndirectos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;


@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CostosIndirectosDTO {

    @Positive(message = "El id debe de ser positivo")
    private Long codigo_ci;
    @NotBlank
    @Positive(message = "El precio debe de ser positivo")
    //OJO A ESTA VALIDACION
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener máximo 10 dígitos entero y 2 decimales")
    private BigDecimal precio;
    @Positive(message = "El id debe de ser positivo")
    private Long codigo_m;
    @Positive(message = "El id deberia de ser psoitivo")
    private Long codigo_e;
}
