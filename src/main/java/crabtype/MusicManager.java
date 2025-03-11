package crabtype;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class MusicManager {
    private static MediaPlayer mediaPlayer;
    private static boolean isMusicOn = true; // Estado de la música

    public static void startMusic() {
        if (mediaPlayer == null) { // Solo se crea una vez
            URL musicURL = MusicManager.class.getResource("/crabtypeInterfaz/sounds/crabtype.mp3");
            if (musicURL != null) {
                Media media = new Media(musicURL.toExternalForm());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.play();
            } else {
                System.out.println("No se encontró el archivo de música.");
            }
        } else {
            // Si ya existe, asegurarse de que sigue sonando
            if (isMusicOn && mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
                mediaPlayer.play();
            }
        }
    }

    public static void toggleMusic() {
        if (mediaPlayer != null) {
            if (isMusicOn) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.play();
            }
            isMusicOn = !isMusicOn;
        }
    }

    public static boolean isMusicPlaying() {
        return isMusicOn;
    }
}
