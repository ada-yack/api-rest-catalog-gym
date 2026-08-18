package gym.ada.api.controller.test;



import org.springframework.web.bind.annotation.*;
import gym.ada.api.dto.Auth.LoginResponseDto;
import gym.ada.api.dto.Auth.LoginUsuarioDto;
import gym.ada.api.service.implement.UsuarioServiceImplement;

@RestController
@RequestMapping("/api/prueba-login")
public class PruebaLoginController {

    private final UsuarioServiceImplement usuarioService;

    public PruebaLoginController(
            UsuarioServiceImplement usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login-direct")
    public LoginResponseDto login(
            @RequestBody LoginUsuarioDto dto) {

        System.out.println("🔥 PRUEBA LOGIN ENTRÓ AL CONTROLLER");
        System.out.println("EMAIL: " + dto.getEmail());

        return usuarioService.login(dto);
    }
    
    
}
//ola
