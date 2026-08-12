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
	

}
