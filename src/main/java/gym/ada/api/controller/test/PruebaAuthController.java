package gym.ada.api.controller.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prueba-auth")
public class PruebaAuthController {

    @GetMapping("/test")
    public String test() {

        System.out.println(
            "🔥🔥🔥 PRUEBA AUTH LLEGÓ AL CONTROLLER 🔥🔥🔥"
        );

        return "PRUEBA_AUTH_OK";
    }
}