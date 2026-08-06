package gym.ada.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gym.ada.api.model.Usuario;
import gym.ada.api.service.implement.UsuarioServiceImplement;


@RestController // responde con JSON automaticamente
@RequestMapping("/api/usuarios")
public class UsuarioController {
	
	private final UsuarioServiceImplement usuarioService;
	
	public UsuarioController(UsuarioServiceImplement usuarioService) {
        this.usuarioService = usuarioService;
    }
	
	 // GET http://localhost:8080/api/usuarios
    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }
    
 // GET http://localhost:8080/api/usuarios/1
    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }
    
    @PostMapping
    public Usuario guardar(@RequestBody Usuario usuario) {
        return usuarioService.guardar(usuario);
    }
    
   // DELETE http://localhost:8080/api/usuarios/1
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
    }
    
 // ── NUEVO: actualizar avatar ───────────────────────────────
    // PATCH /api/usuarios/{id}/avatar
    // Body: { "avatarUrl": "https://res.cloudinary.com/..." }
    @PatchMapping("/{id}/avatar")
    public ResponseEntity<Usuario> actualizarAvatar(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        String avatarUrl = body.get("avatarUrl");
        if (avatarUrl == null || avatarUrl.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Usuario actualizado = usuarioService.actualizarAvatar(id, avatarUrl);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizado);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    ///
    ///     @RestController → todo lo que retornes se convierte en JSON
    ///     @RequestMapping → ruta base para todos los métodos
    ///     @PathVariable → captura el {id} de la URL
    ///    @RequestBody → convierte el JSON que llega en objeto Java
    ///
    /////////////////////////////////////////////////////////////////////////

}
