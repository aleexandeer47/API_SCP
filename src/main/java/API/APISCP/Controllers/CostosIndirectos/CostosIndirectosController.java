package API.APISCP.Controllers.CostosIndirectos;

import API.APISCP.Exceptions.CostosIndirectos.CostoIndirectoNoEncontrada;
import API.APISCP.Exceptions.CostosIndirectos.CostoIndirectoNoInsertada;
import API.APISCP.Models.ApiResponse.ApiResponse;
import API.APISCP.Models.DTO.CostosIndirectos.CostosIndirectosDTO;
import API.APISCP.Services.CostosIndirectos.CostosIndirectosService;
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
@RestController("/api/CostosIndirectos")
public class CostosIndirectosController {

    private CostosIndirectosService repo;

    @RequestMapping("/obtenerCostosIndirectos")
    public ResponseEntity<ApiResponse<List<CostosIndirectosDTO>>> obtenerCostosIndirectos(){

        List<CostosIndirectosDTO> CostosIndirectos = repo.obtenerCostosI();
        if (CostosIndirectos == null || CostosIndirectos.isEmpty()){
            ResponseEntity.badRequest().body(Map.of("Status" , "Error al obtener las unidades de medida"));
        }
        return ResponseEntity.ok(
                ApiResponse.success("Datos consultados correctamentes" , CostosIndirectos)
        );
    }

    @PostMapping("/nuevoCostoIndirecto")
    public ResponseEntity<ApiResponse<CostosIndirectosDTO>>insertarCostosIndirectos(@Valid @RequestBody CostosIndirectosDTO json){
        if (json == null){
            throw new CostoIndirectoNoEncontrada("Error al recibir y procesar la informacion del costo indirecto");

        }
        CostosIndirectosDTO costosIndirecto = repo.insertar(json);
        if (costosIndirecto == null){
            throw new CostoIndirectoNoInsertada("El costo indirecto no puede ser insertado por una incongruencia en los datos");

        }
        return ResponseEntity.ok(ApiResponse.success("El costo indirecto se ha insertado correctamente: " , costosIndirecto));

    }

    @PutMapping("/actualizarCostosIndirecto/{id}")
    public ResponseEntity<?>ActualizarCostoIndirecto(@Valid @PathVariable Long id, @RequestBody CostosIndirectosDTO json, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> erorres = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> erorres.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(erorres);

        }
        try {
            CostosIndirectosDTO CostosIndirectoActualizado = repo.actualizar(id, json);
            return ResponseEntity.ok(CostosIndirectoActualizado);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error al actualizar el costo indirecto");
        }
    }


    //SUSTITUIR VARIABLES
    @DeleteMapping("/eliminarCostosIndirecto/{id}")
    public ResponseEntity<Map<String, Object>> EliminarCostosIndirecto(
            @PathVariable Long id
    ){
        try{
            if (!repo.eliminar(id)){
                //La eliminacion no se pudo realizar
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Error", "Costos indirecto no encontrado")
                        .body(Map.of(
                                "Error", "NOT FOUND",
                                "Mensjae", "El Costos indirectos no fue encontrado",
                                "Fecha y hora", Instant.now().toString()
                        ));
            }
            //La eliminacion si se ejecuto correctamente
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "mensaje", "Costos indirectos  eliminado exitosamente"
            ));
        }catch (Exception e){
            // Si ocurre cualquier error inesperado, retorna 500 (Internal Server Error)
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",  // Indicador de error
                    "message", "Error al eliminar el costo indirecto",  // Mensaje general
                    "detail", e.getMessage()  // Detalles técnicos del error (para debugging)
            ));
        }
    }
}
