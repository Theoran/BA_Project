package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.awt.*;

public class GameOverMenu {


    // Canvas und GraphicsContext
    private static Canvas gameOverCanvas = new Canvas();
    private static GraphicsContext gc = gameOverCanvas.getGraphicsContext2D();

    // Layout des GameOver-Menüs
    private static Group root = new Group();

    private static Scene gameOverScene = new Scene(root, 1280, 600);

    // Hintergrund
    private static Image bgImage = new Image("Pictures/Background/GameOver.png");
    private static Button backToStartScreenButton = new Button("Zurück zum Hauptmenü");




    public static void initialize(Stage stage) {
        root.getChildren().addAll(gameOverCanvas, backToStartScreenButton);

        backToStartScreenButton.setOnKeyPressed(
                e -> {
                    MainMenu.initialize(stage);
                    System.out.println("Zurück zum Hauptmenü...");
                }
        );

        stage.setScene(gameOverScene);
        stage.centerOnScreen();
        gc.drawImage(bgImage, 0, 0);
    }
}
