package gym.ada.api.config;



import java.io.FileInputStream;
import java.io.IOException;

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

        FileInputStream serviceAccount =
                new FileInputStream(
                    "src/main/java/gym/ada/api/security/firebase/firebase-service-account.json"
                );

        FirebaseOptions options =
                FirebaseOptions.builder()
                    .setCredentials(
                        GoogleCredentials.fromStream(serviceAccount)
                    )
                    .build();

        return FirebaseApp.initializeApp(options);
    }
}
