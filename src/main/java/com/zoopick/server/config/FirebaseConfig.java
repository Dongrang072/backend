package com.zoopick.server.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Configuration
public class FirebaseConfig {
    @PostConstruct
    public void setupFirebase() {
        if (!System.getenv().containsKey("FIREBASE_ACCOUNT_KEY_PATH"))
            throw new IllegalStateException("Environment variable FIREBASE_ACCOUNT_KEY_PATH is not set!");
        try {
            String accountKeyPath = System.getenv("FIREBASE_ACCOUNT_KEY_PATH");
            FileInputStream serviceAccount = new FileInputStream(accountKeyPath);

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException exception) {
            log.error(exception.getMessage(), exception);
        }
    }
}
