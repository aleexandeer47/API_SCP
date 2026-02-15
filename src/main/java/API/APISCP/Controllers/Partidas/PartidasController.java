package API.APISCP.Controllers.Partidas;


import API.APISCP.Exceptions.CostosIndirectos.CostoIndirectoNoEncontrada;
import API.APISCP.Exceptions.CostosIndirectos.CostoIndirectoNoInsertada;
import API.APISCP.Exceptions.Partidas.PartidaNoEncontrada;
import API.APISCP.Exceptions.Partidas.PartidaNoInsertada;
import API.APISCP.Models.ApiResponse.ApiResponse;
import API.APISCP.Models.DTO.CostosIndirectos.CostosIndirectosDTO;
import API.APISCP.Models.DTO.Partidas.PartidasDTO;
import API.APISCP.Services.Partidas.PartidaService;
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
@RestController("/api/Partidas")
public class PartidasController {

    private PartidaService repo;

    @RequestMapping("/obtenerPartidas")
    public ResponseEntity<ApiResponse<List<PartidasDTO>>> obtenerPartidas(){

        List<PartidasDTO> partidas = repo.obtenerPartidas();
        if (partidas == null || partidas.isEmpty()){
            ResponseEntity.badRequest().body(Map.of("Status" , "Error al obtener las unidades de medida"));
        }
        return ResponseEntity.ok(
                ApiResponse.success("Datos consultados correctamentes" , partidas)
        );
    }

    @PostMapping("/nuevaPartida")
    public ResponseEntity<ApiResponse<PartidasDTO>>InsertarPartidas(@Valid @RequestBody PartidasDTO json){
        if (json == null){
            throw new PartidaNoEncontrada("Error al recibir y procesar la informacion de la partida");

        }
        PartidasDTO partidasDTO = repo.insertar(json);
        if (partidasDTO == null){
            throw new PartidaNoInsertada("La partida no puede ser insertado por una incongruencia en los datos");

        }
        return ResponseEntity.ok(ApiResponse.success("El costo indirecto se ha insertado correctamente: " , partidasDTO));

    }

    @PutMapping("/actualizarPartidas/{id}")
    public ResponseEntity<?>ActualizarPartidas(@Valid @PathVariable Long id, @RequestBody PartidasDTO json, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> erorres = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> erorres.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(erorres);

        }
        try {
            PartidasDTO PartidasActualizadas = repo.actualizar(id, json);
            return ResponseEntity.ok(PartidasActualizadas);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error al actualizar la partida");
        }
    }


    //SUSTITUIR VARIABLES
    @DeleteMapping("/eliminarPartidas/{id}")
    public ResponseEntity<Map<String, Object>> EliminarPartida(
            @PathVariable Long id
    ){
        try{
            if (!repo.eliminar(id)){
                //La eliminacion no se pudo realizar
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Error", "Partida no encontrada")
                        .body(Map.of(
                                "Error", "NOT FOUND",
                                "Mensjae", "La partida no fue encontrado",
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
