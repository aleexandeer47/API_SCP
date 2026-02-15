package API.APISCP.Controllers.Materiales;

import API.APISCP.Exceptions.CostosIndirectos.CostoIndirectoNoEncontrada;
import API.APISCP.Exceptions.CostosIndirectos.CostoIndirectoNoInsertada;
import API.APISCP.Exceptions.Materiales.MaterialNoEncontrado;
import API.APISCP.Exceptions.Materiales.MaterialesNoInsertados;
import API.APISCP.Models.ApiResponse.ApiResponse;
import API.APISCP.Models.DTO.CostosIndirectos.CostosIndirectosDTO;
import API.APISCP.Models.DTO.Materiales.MaterialesDTO;
import API.APISCP.Services.Materiales.MaterialesService;
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
@RestController("/api/Materiales")
public class MaterialesController {

    private MaterialesService repo;

    @RequestMapping("/obtenerMateriales")
    public ResponseEntity<ApiResponse<List<MaterialesDTO>>> obtenerMateriales(){

        List<MaterialesDTO> materiales = repo.obtenerMateriales();
        if (materiales == null || materiales.isEmpty()){
            ResponseEntity.badRequest().body(Map.of("Status" , "Error al obtener los materiales"));
        }
        return ResponseEntity.ok(
                ApiResponse.success("Datos consultados correctamentes" , materiales)
        );
    }

    @PostMapping("/nuevoMateriales")
    public ResponseEntity<ApiResponse<MaterialesDTO>>insertarMateriales(@Valid @RequestBody MaterialesDTO json){
        if (json == null){
            throw new MaterialNoEncontrado("Error al recibir y procesar la informacion del material");

        }
        MaterialesDTO materiales = repo.insertar(json);
        if (materiales == null){
            throw new MaterialesNoInsertados("El material no puede ser insertado por una incongruencia en los datos");

        }
        return ResponseEntity.ok(ApiResponse.success("El material se ha insertado correctamente: " , materiales));

    }

    @PutMapping("/actualizarMateriales/{id}")
    public ResponseEntity<?>ActualizarMaterial(@Valid @PathVariable Long id, @RequestBody MaterialesDTO json, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> erorres = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> erorres.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(erorres);

        }
        try {
            MaterialesDTO materialActualizado = repo.actualizar(id, json);
            return ResponseEntity.ok(materialActualizado);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error al actualizar el material");
        }
    }


    //SUSTITUIR VARIABLES
    @DeleteMapping("/EliminarMateriales/{id}")
    public ResponseEntity<Map<String, Object>> EliminarMateriales(
            @PathVariable Long id
    ){
        try{
            if (!repo.eliminar(id)){
                //La eliminacion no se pudo realizar
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Error", "Material no encontrado")
                        .body(Map.of(
                                "Error", "NOT FOUND",
                                "Mensjae", "El Material no fue encontrado",
                                "Fecha y hora", Instant.now().toString()
                        ));
            }
            //La eliminacion si se ejecuto correctamente
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "mensaje", "Material eliminado exitosamente"
            ));
        }catch (Exception e){
            // Si ocurre cualquier error inesperado, retorna 500 (Internal Server Error)
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",  // Indicador de error
                    "message", "Error al eliminar el Material",  // Mensaje general
                    "detail", e.getMessage()  // Detalles técnicos del error (para debugging)
            ));
        }
    }

}
