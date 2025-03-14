package crabtype;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

private void reiniciarController {
    @FXML private Button btnComenzar;

    btnReiniciar.setOnAction(event -> reintentar());

    @FXML
    private void reintentar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/crabtypeInterfaz/crabtypeJuego.fxml"));
            Parent root = loader.load();

            // Obtener la ventana actual y cambiar la escena
            Stage stage = (Stage) btnComenzar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar crabtypeJuego.fxml");
        }
    }
}
