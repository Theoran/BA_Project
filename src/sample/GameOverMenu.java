package sample;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.awt.*;

public class GameOverMenu {


    // Canvas und GraphicsContext
    private Canvas gameOverCanvas = new Canvas();
    private GraphicsContext gc = gameOverCanvas.getGraphicsContext2D();

    // Layout des GameOver-Menüs
    private VBox root = new VBox(50);

    private Scene gameOverScene = new Scene(root, 300, 300);

    // Hintergrund
    private Image bgImage = new Image("Pictures/Background/GameOver.png");
    BackgroundImage bg = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

    private Button backToStartScreenButton = new Button("Zurück zum Hauptmenü");


    public void initialize(Stage stage) {
        root.getChildren().addAll(gameOverCanvas, backToStartScreenButton);
        root.setAlignment(Pos.TOP_CENTER);
        root.setBackground(new Background(bg));
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
