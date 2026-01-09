package API.APISCP.Models.DTO.CategoriaMaterial;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CategoriaMaterialDTO {

//  VER SI INT ES LO CORRECTO YA QUE ANTES SE OCUPABA LONG
@Positive(message = "El id debe ser positivo")
private Long codigo_cm;
@NotBlank
private String descripcion;
@Positive(message = "El id debe ser positivo")
private Long codigo_e;

}
