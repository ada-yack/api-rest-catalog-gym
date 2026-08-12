package gym.ada.api.security;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gym.ada.api.dto.UsuarioDto;
import gym.ada.api.dto.Auth.LoginResponseDto;
import gym.ada.api.dto.Auth.LoginUsuarioDto;
import gym.ada.api.dto.Auth.RegistroUsuarioDto;
import gym.ada.api.service.implement.UsuarioServiceImplement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioServiceImplement usuarioService;

    public AuthController(UsuarioServiceImplement usuarioService) {
        this.usuarioService = usuarioService;
    }


    @PostMapping("/registro")
    public ResponseEntity<UsuarioDto> registrar(
            @Valid @RequestBody RegistroUsuarioDto dto) {

        return ResponseEntity.ok(
                usuarioService.registrar(dto)
        );
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginUsuarioDto dto) {

        return ResponseEntity.ok(
                usuarioService.login(dto)
        );
    }
}