package gym.ada.api.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

        String firebaseJson = System.getenv("FIREBASE_SERVICE_ACCOUNT");

        if (firebaseJson == null || firebaseJson.isBlank()) {
            throw new IllegalStateException(
                "No se encontró la variable FIREBASE_SERVICE_ACCOUNT"
            );
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(
                    GoogleCredentials.fromStream(
                        new ByteArrayInputStream(
                            firebaseJson.getBytes(StandardCharsets.UTF_8)
                        )
                    )
                )
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
