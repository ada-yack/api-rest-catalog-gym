package gym.ada.api.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseApp() throws IOException {

        // Si Firebase ya está inicializado, reutilizarlo
        if (!FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.getInstance();
        }

        String firebaseBase64 = System.getenv("FIREBASE_SERVICE_ACCOUNT_BASE64");

        if (firebaseBase64 == null || firebaseBase64.isBlank()) {
            throw new IllegalStateException(
                "No se encontró la variable FIREBASE_SERVICE_ACCOUNT_BASE64"
            );
        }

        byte[] decoded = Base64.getDecoder().decode(firebaseBase64);

        GoogleCredentials credentials =
            GoogleCredentials.fromStream(
                new ByteArrayInputStream(decoded)
            );

        FirebaseOptions options =
            FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();

        return FirebaseApp.initializeApp(options);
    }
}