package gym.ada.api.security.firebase;

import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

@Service
public class FirebaseService {

    public FirebaseToken verificarToken(String token) {

        try {

            return FirebaseAuth
                    .getInstance()
                    .verifyIdToken(token);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Token de Firebase inválido",
                    e
            );
        }
    
    }
}