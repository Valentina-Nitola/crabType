package crabtype.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.HashMap;

public class juegoController {
    @FXML private Label lblTiempo;
    @FXML private Label lblNivel;
    @FXML private Label lblFrase;
    @FXML private Label lblError;
    @FXML private TextArea txtEntrada;
    @FXML private ImageView sol;
    @FXML private Button btnMusica;

    private final String[] frases = {
            "Sol", "Mar", "Luz", "Casa", "Gato", "Perro", "Agua", "Luna", "Mesa", "Mano", "Rio", "Flor", "Pan", "Dia", "Roca", "Vaso", "Calle", "Cielo", "Fuego", "Dedo", "Silla", "Oso", "Viento", "Nube", "Nieve", "Llama", "Llave", "Rayo", "Rojo", "Verde", "Azul", "Oro", "Miel", "Muro", "Pozo", "Voz", "Tela", "Sombra", "Mago", "Cine", "Carta", "Pato", "Lente", "Dama", "Copa", "Joya", "Caña", "Pez", "Salto", "Reloj"};
    private final HashMap<Integer, String> solImagenes = new HashMap<>();

    private int nivel = 1;
    private int tiempo = 20;
    private int vidas = 4;
    private Timeline timeline;

    private static MediaPlayer mediaPlayer;
    private static boolean isMusicOn = true;

    @FXML
    public void initialize() {
        cargarImagenesSol();
        iniciarMusica();
        actualizarBotonMusica();
        iniciarNivel();
        lblError.setVisible(false); // Ocultar mensaje de error al inicio

        btnMusica.setOnAction(event -> toggleMusica());
        txtEntrada.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                validarPalabra();
            }
        });
    }

    private void cargarImagenesSol() {
        solImagenes.put(4, "/crabtypeInterfaz/img/solTotal.png");
        solImagenes.put(3, "/crabtypeInterfaz/img/sol25.png");
        solImagenes.put(2, "/crabtypeInterfaz/img/sol50.png");
        solImagenes.put(1, "/crabtypeInterfaz/img/sol75.png");
        solImagenes.put(0, "/crabtypeInterfaz/img/luna.png");
    }

    private void iniciarNivel() {
        lblNivel.setText("Nivel: " + nivel);
        lblFrase.setText(frases[new Random().nextInt(frases.length)]);
        txtEntrada.clear();
        lblError.setVisible(false); // Ocultar mensaje de error al iniciar nuevo nivel
        iniciarTemporizador();
    }

    private void iniciarTemporizador() {
        if (timeline != null) timeline.stop();
        tiempo = Math.max(2, 20 - (nivel / 5) * 2);
        lblTiempo.setText(String.valueOf(tiempo));

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            tiempo--;
            lblTiempo.setText(String.valueOf(tiempo));
            if (tiempo <= 0) manejarError();
        }));
        timeline.setCycleCount(tiempo);
        timeline.play();
    }

    private void validarPalabra() {
        if (txtEntrada.getText().trim().equals(lblFrase.getText().trim())) {
            timeline.stop();
            nivel++;
            iniciarNivel();
        } else {
            manejarError();
        }
    }

    private void manejarError() {
        timeline.stop();
        sonarError();
        vidas--;
        actualizarSol();
/*
        System.out.println("Error detectado - Mostrando mensaje"); // Depuración
        mostrarMensajeError();
*/
        if (vidas > 0) {
            iniciarNivel();
        } else {
            cargarPantallaReinicio();
        }
    }

/*
    private void mostrarMensajeError() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("¡Palabra incorrecta!");
        alert.showAndWait();
    }
*/
    private void sonarError() {
        URL errorSoundURL = getClass().getResource("/crabtypeInterfaz/sounds/error.mp3");
        if (errorSoundURL != null) {
            Media errorSound = new Media(errorSoundURL.toExternalForm());
            MediaPlayer errorPlayer = new MediaPlayer(errorSound);
            errorPlayer.play();
        } else {
            System.out.println("⚠ No se encontró el archivo de sonido de error.");
        }
    }

    private void cargarPantallaReinicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/crabtypeInterfaz/crabtypeReiniciar.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) lblFrase.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar crabtypeReinicio.fxml");
        }
    }

    private void actualizarSol() {
        if (solImagenes.containsKey(vidas)) {
            sol.setImage(new Image(getClass().getResource(solImagenes.get(vidas)).toExternalForm()));
        }
    }

    private void iniciarMusica() {
        if (mediaPlayer == null) {
            URL musicURL = getClass().getResource("/crabtypeInterfaz/sounds/crabtype.mp3");
            if (musicURL != null) {
                Media media = new Media(musicURL.toExternalForm());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                if (isMusicOn) {
                    mediaPlayer.play();
                }
            } else {
                System.out.println("⚠ No se encontró el archivo de música.");
            }
        }
    }

    private void toggleMusica() {
        if (mediaPlayer != null) {
            if (isMusicOn) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.play();
            }
            isMusicOn = !isMusicOn;
            actualizarBotonMusica();
        }
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
