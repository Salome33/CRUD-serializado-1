package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("views/persona-view.fxml"));
            Parent root = loader.load();

            // Crear la escena
            Scene scene = new Scene(root, 1000, 700);

            // Configurar el stage (ventana principal)


            primaryStage.setTitle("Sistema CRUD de Gestión de Personas");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true);
            primaryStage.setMinWidth(800);
            primaryStage.setMinHeight(600);

            // Mostrar la ventana
            primaryStage.show();

            System.out.println("✓ Interfaz JavaFX cargada exitosamente");

        } catch (Exception e) {
            System.err.println("✗ Error al cargar la interfaz JavaFX");
            System.err.println("Mensaje: " + e.getMessage());
            System.err.println("\nDetalles del error:");
            e.printStackTrace();

            // Verificar si el archivo FXML existe
            if (getClass().getResource("views/persona-view.fxml") == null) {
                System.err.println("\n⚠ El archivo persona-view.fxml NO se encontró en gui/views/");
                System.err.println("Verifica que el archivo existe en: src/gui/views/persona-view.fxml");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Iniciando aplicación JavaFX ===");
        launch(args);
    }
}