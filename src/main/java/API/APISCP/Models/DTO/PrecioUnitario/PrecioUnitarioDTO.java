package API.APISCP.Models.DTO.PrecioUnitario;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.websocket.server.ServerEndpoint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PrecioUnitarioDTO {

    @Positive(message = "El id debe de ser positivo")
    private Long codigo_pu;
    @NotBlank
    @Digits(integer = 10, fraction = 2, message = "El precio debe tener máximo 10 dígitos entero y 2 decimales")
    private BigDecimal precio;
    @NotBlank
    @Digits(integer = 10, fraction = 2, message = "La presentación debe tener máximo 10 dígitos entero y 2 decimales")
    private BigDecimal presentacion;
    @NotBlank
    @Digits(integer = 10, fraction = 2, message = "El precio unitario debe tener máximo 10 dígitos entero y 2 decimales")
    private BigDecimal precio_unitario;
    @Positive(message = "El id debe de ser positivo")
    private Long codigo_m;
    @Positive(message = "El id debe de ser positivo")
    private Long codigo_u;
    @Positive(message = "El id debe de ser positivo")
    private Long codigo_e;


}
