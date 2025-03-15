package crabtype.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Clase principal que inicia la aplicación CrabType utilizando JavaFX.
 * Carga la interfaz de usuario definida en un archivo FXML y la muestra en una ventana principal.
 * @author Valentina Nitola Alarcon
 * @version 1.0.0
 */
public class CrabTypeMain extends Application {

    /**
     * Método de inicio de la aplicación JavaFX.
     * Carga la interfaz gráfica desde un archivo FXML y la establece en el escenario principal.
     *
     *
     * @param primaryStage El escenario principal donde se mostrará la interfaz de usuario.
     * @throws Exception Si ocurre un error al cargar el archivo FXML.
     *
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/crabtypeInterfaz/crabtypeMenu.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setTitle("CrabType");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Método principal que inicia la aplicación JavaFX.
     *
     * @param args Argumentos de la línea de comandos.
     *
     */
    public static void main(String[] args) {
        launch(args);
    }
}
