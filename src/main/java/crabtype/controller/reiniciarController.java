package crabtype.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button; // Importación correcta

import java.io.IOException;

public class reiniciarController {
    @FXML private Button btnReiniciar;
    @FXML private Button btnExit;

    @FXML
    public void initialize() {
        btnReiniciar.setOnAction(event -> reiniciar());
        btnExit.setOnAction(event -> cerrarJuego()); // Asigna la acción al botón salir
    }

    private void reiniciar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/crabtypeInterfaz/crabtypeJuego.fxml"));
            Parent root = loader.load();

            // Obtener la ventana actual y cambiar la escena
            Stage stage = (Stage) btnReiniciar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar crabtypeJuego.fxml");
        }
    }

    private void cerrarJuego() {
        System.exit(0); // Cierra la aplicación
    }
}
