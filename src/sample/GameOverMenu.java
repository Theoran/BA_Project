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
    private Canvas gameOverCanvas = new Canvas();
    private GraphicsContext gc = gameOverCanvas.getGraphicsContext2D();

    // Layout des GameOver-Menüs
    private Group root = new Group();

    private Scene gameOverScene = new Scene(root, 1280, 600);

    // Hintergrund
    private Image bgImage = new Image("Pictures/Background/GameOver.png");
    private Button backToStartScreenButton = new Button("Zurück zum Hauptmenü");


    public void initialize(Stage stage) {
        root.getChildren().addAll(gameOverCanvas, backToStartScreenButton);
        gc.drawImage(bgImage, 0, 0);

        backToStartScreenButton.setOnAction(
                e -> {
                    MainMenu mainMenu = new MainMenu();
                    mainMenu.initialize(stage);
                    System.out.println("Zurück zum Hauptmenü...");
                }
        );

        stage.setScene(gameOverScene);
        stage.centerOnScreen();
    }
}
