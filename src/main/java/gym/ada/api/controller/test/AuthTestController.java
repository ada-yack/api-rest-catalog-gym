package gym.ada.api.controller.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthTestController {

    @GetMapping("/test")
    public String test() {

        System.out.println(
            "🔥 AUTH TEST: llegó a Spring"
        );

        return "AUTH_ENDPOINT_OK";
    }

    @PostMapping("/test")
    public String testPost() {

        System.out.println(
            "🔥 AUTH TEST POST: llegó a Spring"
        );

        return "AUTH_POST_OK";
    }
    
    @PostMapping("/login-debug")
    public ResponseEntity<String> loginDebug(
            @RequestBody String body) {

        System.out.println("================================");
        System.out.println("🔥 LOGIN DEBUG LLEGO AL CONTROLLER");
        System.out.println("BODY: " + body);
        System.out.println("================================");

        return ResponseEntity.ok("LOGIN_DEBUG_OK");
    }
    
}