package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu {

    // Layout des Main Menues inklusive Zuweisung und Positionierung der Buttons
    private static VBox menuLayout = new VBox(50);

    // Buttons zum Start und Verlassen des Spiels
    private static Button startButton = new Button("Start Game");
    private static Button exitButton = new Button("Exit");

    private static Scene menu = new Scene(menuLayout, 300, 300);



    public static void initialize(Stage stage) {
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.getChildren().addAll(startButton, exitButton);

        // Eventhandler für Start-Button
        startButton.setOnAction(e -> {
            GameScene.initialize(stage);
            GameScene.GameLoop.start();
            Sound.music(Sound.musicList[(int) Math.round(Math.random())], 0.1);
        });

        // Eventhandler für Exit-Button
        exitButton.setOnAction(e -> {
            stage.close();
            System.out.println("Spiel beendet...");
        });

        stage.setScene(menu);
        stage.show();
    }
}
