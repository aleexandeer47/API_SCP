package API.APISCP.Models.DTO.CostosUnitarios;

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
@ToString
@EqualsAndHashCode
public class CostosUnitariosDTO {

    @Positive(message = "El id debe de ser positivo")
    private Long codigo_cu;
    @NotBlank
    @Positive(message = "La cantidad de material debe de ser un numero positivo")
    private BigDecimal cantidad_material;
    @Digits(integer = 10, fraction = 2, message = "El rendimiento debe tener máximo 10 dígitos entero y 2 decimales")
    private BigDecimal rendimiento;
    @Positive(message = "El calculo del precio unitario debe de ser positivo")
    @NotBlank
    private BigDecimal calculo_precioU;
    @Positive(message = "El resultado debe de ser positivo")
    @Digits(integer = 10, fraction = 2, message = "El total debe tener máximo 10 dígitos entero y 2 decimales")
    private BigDecimal total;
    @Positive(message = "El id debe de ser positivo")
    private Long codigo_m;
    @Positive(message = "El id debe de ser positivo")
    private Long codigo_p;
    @Positive(message = "El id debe de ser positivo")
    private Long codigo_ci;
    @Positive(message = "El id debe de ser positivo")
    private Long codigo_e;
    @Positive(message = "El id debe de ser positivo")
    private Long codigo_pu;

}
