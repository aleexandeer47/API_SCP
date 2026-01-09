package API.APISCP.Models.DTO.Partidas;

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
public class PartidasDTO {

    @Positive(message = "El id debe de ser positivo")
    private Long codigo_p;
    @NotBlank
    private String descripcion;
    @NotBlank
    private String unidad_medida;
    @Digits(integer = 10, fraction = 2, message = "La cantidad analizada debe tener máximo 10 dígitos entero y 2 decimales")
    private BigDecimal cantidad_analizada;
    @Digits(integer = 10, fraction = 2, message = "La produccion por dia debe tener máximo 10 dígitos entero y 2 decimales")
    private BigDecimal produccion_xdia;
    @Positive(message = "El id debe de ser positivo")
    private Long codigo_e;
}
