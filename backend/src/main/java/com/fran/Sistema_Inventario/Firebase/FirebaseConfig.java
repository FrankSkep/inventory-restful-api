package com.fran.Sistema_Inventario.Firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.File;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebaseApp() throws IOException {
        // Ruta al archivo de credenciales descargado desde Firebase Console
        FileInputStream serviceAccount
                = new FileInputStream("src/main/resources/serviceAccountKey.json");

        // Usando el builder para crear las opciones de Firebase
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("productos-inventario-7f2d7.appspot.com")
                .build();

        // Inicializar la aplicación de Firebase con las opciones configuradas
        return FirebaseApp.initializeApp(options);
    }
}
