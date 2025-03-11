package crabtype;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.net.URL;
import java.util.Random;
import java.util.HashMap;

public class juegoController {
    @FXML private Label lblTiempo;
    @FXML private Label lblNivel;
    @FXML private Label lblFrase;
    @FXML private TextArea txtEntrada;
    @FXML private ImageView sol;
    @FXML private Button btnMusica;

    private final String[] frases = {"JavaFX", "Programacion", "Evento", "Interface", "Desarrollo"};
    private final HashMap<Integer, String> solImagenes = new HashMap<>();

    private int nivel = 1;
    private int tiempo = 20;
    private int vidas = 4;
    private Timeline timeline;

    // 🎵 Variables de música (static para evitar múltiples instancias)
    private static MediaPlayer mediaPlayer;
    private static boolean isMusicOn = true;

    private final Image imgSonidoOn = new Image(getClass().getResource("/crabtypeInterfaz/img/sonidoOn.png").toExternalForm());
    private final Image imgSonidoOff = new Image(getClass().getResource("/crabtypeInterfaz/img/sonidoOff.png").toExternalForm());

    @FXML
    public void initialize() {
        cargarImagenesSol();
        iniciarMusica(); // 🔥 Se asegura que la música solo se inicie una vez
        actualizarBotonMusica();
        iniciarNivel();

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
        vidas--;
        actualizarSol();
        if (vidas > 0) {
            iniciarNivel();
        } else {
            lblFrase.setText("Juego Terminado");
            txtEntrada.setDisable(true);
        }
    }

    private void actualizarSol() {
        if (solImagenes.containsKey(vidas)) {
            sol.setImage(new Image(getClass().getResource(solImagenes.get(vidas)).toExternalForm()));
        }
    }

    // 🎵 Métodos de Música 🎵
    private void iniciarMusica() {
        if (mediaPlayer == null) { // Solo se inicializa si no existe un MediaPlayer
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
        if (isMusicOn) {
            btnMusica.setGraphic(new ImageView(imgSonidoOn));
        } else {
            btnMusica.setGraphic(new ImageView(imgSonidoOff));
        }
    }
}
