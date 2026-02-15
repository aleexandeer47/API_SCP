package API.APISCP.Controllers.Usuarios;

import API.APISCP.Exceptions.Usuarios.UsuarioNoInsertado;
import API.APISCP.Models.ApiResponse.ApiResponse;
import API.APISCP.Models.DTO.Usuarios.UsuariosDTO;
import API.APISCP.Services.Usuarios.UsuarioService;
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
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/obtenerUsuarios")
    public ResponseEntity<ApiResponse<List<UsuariosDTO>>> obtenerUsuarios() {
        List<UsuariosDTO> usuarios = usuarioService.obtenerUsuarios();
        if (usuarios == null || usuarios.isEmpty()) {
                    ResponseEntity.badRequest().body(Map.of(
                        "Status" , "Error al obtener los usuarios"
                    )
            );
        }
        return ResponseEntity.ok(
                ApiResponse.success("Datos consultados correctamente", usuarios)
        );
    }

    @PostMapping("/nuevoUsuario")
    public ResponseEntity<ApiResponse<UsuariosDTO>>insertarUsuario(@Valid @RequestBody UsuariosDTO json){
        if (json == null){
            throw  new UsuarioNoInsertado("Error al recibir y procesar la informacion del usuario");
        }
        UsuariosDTO usuarioGuardado = usuarioService.insertar(json);
        if (usuarioGuardado == null){
            throw  new UsuarioNoInsertado("El usuario no puedo ser registrado por alguna incongruencia con los datos");

        }
        return ResponseEntity.ok(ApiResponse.success("El usuario se inserto correctamente " , usuarioGuardado));
    }


   @PutMapping("/actualizarUsuarios/{id}")
    public ResponseEntity<?> actualizarUsuario(@Valid @PathVariable Long id, @RequestBody UsuariosDTO json, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try{
            UsuariosDTO usuarioActualizado = usuarioService.actualizar(id, json);
            return ResponseEntity.ok(usuarioActualizado);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Error al actualizar el usuario");
        }
   }

    @DeleteMapping("/eliminarUsuario/{id}")
    public ResponseEntity<Map<String, Object>> eliminarUsuario(
            @PathVariable Long id
    ){
        try{
            if (!usuarioService.eliminar(id)){
                //La eliminacion no se pudo realizar
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Error", "El usuario no encontrado")
                        .body(Map.of(
                                "Error", "NOT FOUND",
                                "Mensjae", "El usuario no fue encontrado",
                                "Fecha y hora", Instant.now().toString()
                        ));
            }
            //La eliminacion si se ejecuto correctamente
            return ResponseEntity.ok().body(Map.of(
                    "status", "Proceso completado",
                    "mensaje", "Usuario eliminado exitosamente"
            ));
        }catch (Exception e){
            // Si ocurre cualquier error inesperado, retorna 500 (Internal Server Error)
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "Error",  // Indicador de error
                    "message", "Error al eliminar el usuario",  // Mensaje general
                    "detail", e.getMessage()  // Detalles técnicos del error (para debugging)
            ));
        }
    }

    @PutMapping("/actualizar/{id}/contrasena")
    private ResponseEntity<Map<String, Object>> resetPassword(@Valid @PathVariable Long id){
        try{
            boolean respuesta = usuarioService.cambiarContrasena(id);
            if (respuesta){
                return ResponseEntity.ok().body(Map.of(
                        "Success", "Proceso completado exitosamente",
                        "Message", "La contrasena fue restablecida correctamente"
                ));
            }
            return ResponseEntity.ok().body(Map.of(
                    "Status", "Error",
                    "Message", "El proceso no pudo ser completado"
            ));
        }catch (Exception e){
            return ResponseEntity.ok().body(Map.of(
                    "Status", "Proceso interrumpido",
                    "Message", "El proceso no pudo ser completado"
            ));
        }
    }



}
