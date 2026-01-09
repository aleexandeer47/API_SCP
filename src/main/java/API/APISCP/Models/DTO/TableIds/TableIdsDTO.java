package API.APISCP.Models.DTO.TableIds;

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
public class TableIdsDTO {

    @Positive(message = "El id debe de ser positivo")
    private Long codigo_tabla;
    @NotBlank
    private String nombre_tabla;
    @NotBlank
    private Long correlativo;
}
