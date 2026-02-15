package API.APISCP.Controllers.UnidadesMedida;

import API.APISCP.Exceptions.UnidadesMedida.UnidadMedidaNoInsertado;
import API.APISCP.Models.ApiResponse.ApiResponse;
import API.APISCP.Models.DTO.UnidadesMedida.UnidadesMedidaDTO;
import API.APISCP.Models.DTO.Usuarios.UsuariosDTO;
import API.APISCP.Services.UnidadesMedida.UnidadesMedidaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController("/api/UnidadesMedida")
public class UnidadesMedidaController {

    @Autowired
    private UnidadesMedidaService repo;

    @RequestMapping("/obtenerUnidadesMedida")
    public ResponseEntity<ApiResponse<List<UnidadesMedidaDTO>>>obtenerUsuarios(){

        List<UnidadesMedidaDTO> UnidadesMedida = repo.obtenerUnidadesM();
        if (UnidadesMedida == null || UnidadesMedida.isEmpty()){
            ResponseEntity.badRequest().body(Map.of("Status" , "Error al obtener las unidades de medida"));
        }
        return ResponseEntity.ok(
                ApiResponse.success("Datos consultados correctamentes" , UnidadesMedida)
        );

    }


    @PostMapping("/nuevoUnidadMedida")
    public ResponseEntity<ApiResponse<UnidadesMedidaDTO>>insertarUMedida(@Valid @RequestBody UnidadesMedidaDTO json){
       if (json == null){
           throw new UnidadMedidaNoInsertado("Error al recibir y procesar la informacion del usuario");

       }
       UnidadesMedidaDTO UMedidaGuardado = repo.insertar(json);
       if (UMedidaGuardado == null){
           throw new UnidadMedidaNoInsertado("La unidad de medida no puede ser insertado por una incongruencia en los datos");

       }
       return ResponseEntity.ok(ApiResponse.success("La unidad de medida se ha insertado correctamente: " , UMedidaGuardado));

    }

    @PutMapping("/actualizarUnidadMedida/{id}")
    public ResponseEntity<?>actualizarUnidadesMedida(@Valid @PathVariable Long id, @RequestBody UnidadesMedidaDTO json, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> erorres = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> erorres.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(erorres);

        }
        try {
            UnidadesMedidaDTO unidadesMedidaActualizado = repo.actualizar(id, json);
            return ResponseEntity.ok(unidadesMedidaActualizado);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error al actualizar la unidad de medida");
        }
    }

    @DeleteMapping("/eliminarUnidadMedida/{id}")
    public ResponseEntity<Map<String, Object>> eliminarUnidadMedida(
            @PathVariable Long id
    ){
        try{
            if (!repo.eliminar(id)){
                //La eliminacion no se pudo realizar
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Error", "El usuario no encontrado")
                        .body(Map.of(
                                "Error", "NOT FOUND",
                                "Mensjae", "La unidad de medida no fue encontrado",
                                "Fecha y hora", Instant.now().toString()
                        ));
            }
            //La eliminacion si se ejecuto correctamente
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "mensaje", "Unidad de medida eliminado exitosamente"
            ));
        }catch (Exception e){
            // Si ocurre cualquier error inesperado, retorna 500 (Internal Server Error)
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",  // Indicador de error
                    "message", "Error al eliminar la unidad de medida",  // Mensaje general
                    "detail", e.getMessage()  // Detalles técnicos del error (para debugging)
            ));
        }
    }



}
