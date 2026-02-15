package API.APISCP.Controllers.Roles;

import API.APISCP.Entities.Roles.RolesEntity;
import API.APISCP.Exceptions.Roles.RolNoEncontrado;
import API.APISCP.Exceptions.Roles.RolNoInsertado;
import API.APISCP.Exceptions.UnidadesMedida.UnidadMedidaNoInsertado;
import API.APISCP.Models.ApiResponse.ApiResponse;
import API.APISCP.Models.DTO.Roles.RolesDTO;
import API.APISCP.Models.DTO.UnidadesMedida.UnidadesMedidaDTO;
import API.APISCP.Repositories.Roles.RolesRepository;
import API.APISCP.Services.Roles.RoleService;
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
@RestController("/api/Roles")
public class RolesController {

    @Autowired
    private RoleService repo;

    @RequestMapping("/ObtenerRoles")
    public ResponseEntity<ApiResponse<List<RolesDTO>>>obtenerRoles(){
        List<RolesDTO> Roles = repo.obtenerRol();
        if (Roles == null ||  Roles.isEmpty()){
            ResponseEntity.badRequest().body(Map.of("Status" , "Error al obtener los roles"));

        }
        return ResponseEntity.ok(
                ApiResponse.success("Datos consultados correctamentes" , Roles)
        );
    }

    @PostMapping("/nuevoRol")
    public ResponseEntity<ApiResponse<RolesDTO>>insertarRole(@Valid @RequestBody RolesDTO json){
        if (json == null){
            throw new RolNoEncontrado("Error al recibir y procesar la informacion del usuario");

        }
        RolesDTO rolGuardado = repo.insertar(json);
        if (rolGuardado == null){
            throw new RolNoInsertado("El rol no puede ser insertado por una incongruencia en los datos");

        }
        return ResponseEntity.ok(ApiResponse.success("El rol se ha insertado correctamente: " , rolGuardado));
    }

    @PutMapping("/actualizarRol/{id}")
    public ResponseEntity<?>ActualizarRol(@Valid @PathVariable Long id, @RequestBody RolesDTO json, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> erorres = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> erorres.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(erorres);

        }
        try {
            RolesDTO RolActualizado = repo.actualizar(id, json);
            return ResponseEntity.ok(RolActualizado);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error al actualizar el rol");
        }
    }

    @DeleteMapping("/eliminarRol/{id}")
    public ResponseEntity<Map<String, Object>> eliminarRol(
            @PathVariable Long id
    ){
        try{
            if (!repo.eliminar(id)){
                //La eliminacion no se pudo realizar
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Error", "El rol no se encontro")
                        .body(Map.of(
                                "Error", "NOT FOUND",
                                "Mensjae", "El rol no fue encontrado",
                                "Fecha y hora", Instant.now().toString()
                        ));
            }
            //La eliminacion si se ejecuto correctamente
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "mensaje", "Rol eliminado exitosamente"
            ));
        }catch (Exception e){
            // Si ocurre cualquier error inesperado, retorna 500 (Internal Server Error)
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",  // Indicador de error
                    "message", "Error al eliminar el rol",  // Mensaje general
                    "detail", e.getMessage()  // Detalles técnicos del error (para debugging)
            ));
        }
    }



}
