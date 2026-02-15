package API.APISCP.Controllers.PrecioUnitario;

import API.APISCP.Exceptions.PrecioUnitario.PrecioUnitarioNoEncontrado;
import API.APISCP.Exceptions.UnidadesMedida.UnidadMedidaNoInsertado;
import API.APISCP.Models.ApiResponse.ApiResponse;
import API.APISCP.Models.DTO.PrecioUnitario.PrecioUnitarioDTO;
import API.APISCP.Models.DTO.UnidadesMedida.UnidadesMedidaDTO;
import API.APISCP.Services.PrecioUnitario.PrecioUnitarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RestController("/api/PrecioUnitario")
public class PrecioUnitarioController {

    private PrecioUnitarioService repo;

    @RequestMapping("/obtenerPrecioUnitario")
    public ResponseEntity<ApiResponse<List<PrecioUnitarioDTO>>> obtenerUsuarios(){

        List<PrecioUnitarioDTO> precioUnitario = repo.obtenerPrecioUnitario();
        if (precioUnitario == null || precioUnitario.isEmpty()){
            ResponseEntity.badRequest().body(Map.of("Status" , "Error al obtener las unidades de medida"));
        }
        return ResponseEntity.ok(
                ApiResponse.success("Datos consultados correctamentes" , precioUnitario)
        );
    }

    @PostMapping("/nuevoPrecioUnitario")
    public ResponseEntity<ApiResponse<PrecioUnitarioDTO>>insertarUMedida(@Valid @RequestBody PrecioUnitarioDTO json){
        if (json == null){
            throw new PrecioUnitarioNoEncontrado("Error al recibir y procesar la informacion del usuario");

        }
        PrecioUnitarioDTO precioUnitario = repo.insertar(json);
        if (precioUnitario == null){
            throw new UnidadMedidaNoInsertado("La unidad de medida no puede ser insertado por una incongruencia en los datos");

        }
        return ResponseEntity.ok(ApiResponse.success("La unidad de medida se ha insertado correctamente: " , precioUnitario));

    }

    @PutMapping("/actualizarPrecioUnitario/{id}")
    public ResponseEntity<?>ActualizarPrecioUnitario(@Valid @PathVariable Long id, @RequestBody PrecioUnitarioDTO json, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> erorres = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> erorres.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(erorres);

        }
        try {
            PrecioUnitarioDTO precioUnitarioActualizado = repo.actualizar(id, json);
            return ResponseEntity.ok(precioUnitarioActualizado);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error al actualizar la unidad de medida");
        }
    }


    //SUSTITUIR VARIABLES
    @DeleteMapping("/eliminarPrecioUnitario/{id}")
    public ResponseEntity<Map<String, Object>> eliminarPrecioUnitario(
            @PathVariable Long id
    ){
        try{
            if (!repo.eliminar(id)){
                //La eliminacion no se pudo realizar
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Error", "Precio unitario no encontrado")
                        .body(Map.of(
                                "Error", "NOT FOUND",
                                "Mensjae", "El precio unitario no fue encontrado",
                                "Fecha y hora", Instant.now().toString()
                        ));
            }
            //La eliminacion si se ejecuto correctamente
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "mensaje", "Precio Unitario eliminado exitosamente"
            ));
        }catch (Exception e){
            // Si ocurre cualquier error inesperado, retorna 500 (Internal Server Error)
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",  // Indicador de error
                    "message", "Error al eliminar el precio unitario",  // Mensaje general
                    "detail", e.getMessage()  // Detalles técnicos del error (para debugging)
            ));
        }
    }

}
