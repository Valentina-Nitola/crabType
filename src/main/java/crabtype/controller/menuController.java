package crabtype.controller;

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
import java.net.URL;

public class menuController {

    @FXML private Button btnComenzar;
    @FXML private Button btnMusica;

    public static MediaPlayer mediaPlayer; // Mantenerlo estático para compartirlo
    private boolean isMusicOn = true; // Estado de la música

    @FXML
    private void initialize() {
        // Iniciar música si aún no está creada
        if (mediaPlayer == null) {
            URL musicURL = getClass().getResource("/crabtypeInterfaz/sounds/crabtype.mp3");
            if (musicURL != null) {
                Media media = new Media(musicURL.toExternalForm());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.play();
            } else {
                System.out.println("No se encontró el archivo de música.");
            }
        }

        actualizarBotonMusica();
        btnMusica.setOnAction(event -> toggleMusica());
        btnComenzar.setOnAction(event -> abrirPantallaJuego());
    }

    private void abrirPantallaJuego() {
        try {
            if (mediaPlayer != null && isMusicOn) {
                mediaPlayer.pause(); // Pausar la música antes de cambiar de pantalla
            }

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

    private void toggleMusica() {
        if (isMusicOn) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.play();
        }
        isMusicOn = !isMusicOn;
        actualizarBotonMusica();
    }

    private void actualizarBotonMusica() {
        String imgPath = isMusicOn ? "/crabtypeInterfaz/img/sonidoOn.png" : "/crabtypeInterfaz/img/sonidoOff.png";
        URL imgURL = getClass().getResource(imgPath);
        if (imgURL != null) {
            Image img = new Image(imgURL.toString());
            btnMusica.setGraphic(new ImageView(img));
        } else {
            System.out.println("No se encontró la imagen: " + imgPath);
        }
    }
}
