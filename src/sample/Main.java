package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.audio.*;

import java.io.FileInputStream;
import java.util.ArrayList;


public class Main extends Application {



    public Wave wave;


    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Canvas + Graphicscontext
        Canvas game_menu_c = new Canvas(1280, 720);
        GraphicsContext gc = game_menu_c.getGraphicsContext2D();



        //Buttons zum Start und Verlassen des Spiels
        Button start_game_b = new Button("Start a new Game");
        Button exit_b = new Button("Close");


        //Layout des Main Menues inklusive Zuweisung und Positionierung der Buttons
        VBox main_menue_l = new VBox();
        main_menue_l.setAlignment(Pos.CENTER);
        main_menue_l.setSpacing(10);
        main_menue_l.getChildren().addAll(start_game_b, exit_b);


        //Scene des Main Menues wird initialisiert
        Scene main_menue_s = new Scene(main_menue_l);


        //Main Menu Scene dem Primary Stage zuweisen
        primaryStage.setScene(main_menue_s);
        primaryStage.show();

        //Layout des Game Menues
        Group game_menu_l = new Group();
        game_menu_l.getChildren().addAll(game_menu_c);


        //Scene des Game Menues wird initialisiert
        Scene game_menu_s = new Scene(game_menu_l, 1280, 720);


        //Eventhandler für Start-Button
        start_game_b.setOnAction(e -> {
            primaryStage.setScene(game_menu_s);
            primaryStage.centerOnScreen();
            System.out.println("Spiel gestartet...");

            AudioPlayer soundLoop = AudioPlayer.player;
            AudioStream BGM;
            AudioData MD;
            ContinuousAudioDataStream loop = null;
            try{
                System.out.println("Test");
                BGM = new AudioStream(new FileInputStream("sample/Ultima_Weapon.mp3"));
                MD = BGM.getData();
                loop = new ContinuousAudioDataStream(MD);

            }catch(Exception e1){}

            soundLoop.start(loop);
        });

        //Eventhandler für Exit-Button
        exit_b.setOnAction(e -> {
            primaryStage.close();
            System.out.println("Spiel beendet...");
        });

        // ArrayList für Input
        ArrayList<String> userinput = new ArrayList<>();
        game_menu_s.setOnKeyPressed(
                e -> {
                    String code = e.getCode().toString();
                    if(!userinput.contains(code))
                        userinput.add(code);
                }
        );
        game_menu_s.setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    userinput.remove(code);
                }
        );

        // ArrayList Projektile
        ArrayList<Projectile>projectileList = new ArrayList<>();

        // ArrayList Ships
        ArrayList<Ship>shipList = new ArrayList<>();

        Image space = new Image("sample/Space.jpg");

        // Initialisierung Spielfigur
        Ship playership = new Ship();
        playership.setImage("sample/playerShip1_orange.png");
        playership.setPosition(50, 370);
        playership.setShotImage("sample/laserBlue06.png");
        playership.setShotVelocity(450);
        playership.setShotDmg(100);
        System.out.println(playership.getShotDmg());
        playership.setFriendly();
        shipList.add(playership);

        // Initialisierung der EnemyFactory
        EnemyFactory factory = new EnemyFactory();

        wave = new Wave(4, "sample/enemyBlack1.png", 200);


        new AnimationTimer(){
            int waveCounter = 0;
            long lastNanoTime = (System.nanoTime());
            double elapsedTime;
            double overallTime = 0;
            double timeSinceSpawn = 0;

            public void handle(long currentNanoTime)
            {
                elapsedTime = (currentNanoTime - lastNanoTime)  / 1e9f;
                lastNanoTime = currentNanoTime;
                overallTime += elapsedTime;
                timeSinceSpawn += elapsedTime;

                gc.drawImage(space, 0, 0);

                /* Gegner-Spawn Wave-System
                if(!wave.updateEnemies()) {
                    wave = new Wave(3, "sample/playership1_orange.png", 300);
                }

                ArrayList<EnemyShip> enemyList = wave.getWaveList();
                */

                for (int i=0;i<shipList.size();i++) {
                    Ship ship = shipList.get(i);

                    for (int j=0; j<projectileList.size();j++) {
                        Projectile projectile = projectileList.get(j);
                        if (ship.intersects(projectile) && (ship.getFriendly() != projectile.getFriendly())) {
                            ship.takeDamage(projectile.getDmg());
                            if (ship.getHealth() <= 0) {
                                shipList.remove(ship);
                            }
                            projectileList.remove(projectile);
                        }
                    }

                    ship.update(elapsedTime);
                    ship.render(gc);
                    ship.addToTimeSinceLastShot(elapsedTime);
                }
                for(int i=0;i<projectileList.size();i++) {
                    Projectile projectile = projectileList.get(i);
                    projectile.update(elapsedTime);
                    if (projectile.getPosition_x() >= 1280) projectileList.remove(projectile);
                    projectile.render(gc);
                }

                playership.setVelocity(0, 0);
                if (userinput.contains("UP")) playership.addVelocity(0, -400);
                if (userinput.contains("DOWN")) playership.addVelocity(0, 400);
                if (userinput.contains("LEFT")) playership.addVelocity(-400, 0);
                if (userinput.contains("RIGHT")) playership.addVelocity(400, 0);

                if (userinput.contains("SPACE") && playership.getTimeSinceLastShot() >= 0.3) {
                    Projectile activeProjectile = playership.shoot();
                    activeProjectile.setFriendly();
                    activeProjectile.setDmg(10000);
                    projectileList.add(activeProjectile);
                    playership.resetTimeSinceLastShot();
                }

                // Random-Spawn per Factory
                if (timeSinceSpawn >= 1) {
                    Ship enemy = factory.spawnEnemy(overallTime);
                    shipList.add(enemy);
                    timeSinceSpawn = 0;
                }

                // wave.setWaveList(enemyList);

            } // ENDE HANDLE

        }.start();

    }
}
