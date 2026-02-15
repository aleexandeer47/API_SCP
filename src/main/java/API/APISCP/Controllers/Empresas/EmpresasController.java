package API.APISCP.Controllers.Empresas;

import API.APISCP.Exceptions.Empresas.EmpresaNoEncontrada;
import API.APISCP.Exceptions.Empresas.EmpresaNoInsertada;
import API.APISCP.Models.ApiResponse.ApiResponse;
import API.APISCP.Models.DTO.Empresas.EmpresasDTO;
import API.APISCP.Services.Empresas.EmpresaService;
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
@RestController("/api/Empresas")
public class EmpresasController {

    private EmpresaService repo;

    @RequestMapping("/obtenerEmpresas")
    public ResponseEntity<ApiResponse<List<EmpresasDTO>>> ObtenerEmpresas(){

        List<EmpresasDTO> empresasDTOS = repo.obtenerEmpresas();
        if (empresasDTOS == null || empresasDTOS.isEmpty()){
            ResponseEntity.badRequest().body(Map.of("Status" , "Error al obtener las empresas"));
        }
        return ResponseEntity.ok(
                ApiResponse.success("Datos consultados correctamentes" , empresasDTOS)
        );
    }

    @PostMapping("/nuevaEmpresa")
    public ResponseEntity<ApiResponse<EmpresasDTO>>insertarEmpresa(@Valid @RequestBody EmpresasDTO json){
        if (json == null){
            throw new EmpresaNoEncontrada("Error al recibir y procesar la informacion de la empresa");

        }
        EmpresasDTO empresasDTO = repo.insertar(json);
        if (empresasDTO == null){
            throw new EmpresaNoInsertada("La empresa no puede ser insertado por una incongruencia en los datos");

        }
        return ResponseEntity.ok(ApiResponse.success("La empresa se ha insertado correctamente: " , empresasDTO));

    }

    @PutMapping("/actualizarEmpresa/{id}")
    public ResponseEntity<?>ActualizarEmpresa(@Valid @PathVariable Long id, @RequestBody EmpresasDTO json, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> erorres = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> erorres.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(erorres);

        }
        try {
            EmpresasDTO empresaActualizada = repo.actualizar(id, json);
            return ResponseEntity.ok(empresaActualizada);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error al actualizar la empresa");
        }
    }



    @DeleteMapping("/eliminarEmpresa/{id}")
    public ResponseEntity<Map<String, Object>> EliminarEmpresa(
            @PathVariable Long id
    ){
        try{
            if (!repo.eliminar(id)){
                //La eliminacion no se pudo realizar
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Error", "Empresa no encontrado")
                        .body(Map.of(
                                "Error", "NOT FOUND",
                                "Mensjae", "La empresa no fue encontrado",
                                "Fecha y hora", Instant.now().toString()
                        ));
            }
            //La eliminacion si se ejecuto correctamente
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "mensaje", "Empresa  eliminada exitosamente"
            ));
        }catch (Exception e){
            // Si ocurre cualquier error inesperado, retorna 500 (Internal Server Error)
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",  // Indicador de error
                    "message", "Error al eliminar la empresa",  // Mensaje general
                    "detail", e.getMessage()  // Detalles técnicos del error (para debugging)
            ));
        }
    }
}
