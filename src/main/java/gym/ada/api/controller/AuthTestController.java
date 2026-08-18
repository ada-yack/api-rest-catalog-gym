package gym.ada.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
}